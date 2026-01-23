package com.ecommerce.cart_service.service.impl;

import com.ecommerce.cart_service.common.CartStatus;
import com.ecommerce.cart_service.common.MessageError;
import com.ecommerce.cart_service.dto.request.CartItemRequest;
import com.ecommerce.cart_service.dto.response.CartResponse;
import com.ecommerce.cart_service.dto.response.ProductResponse;
import com.ecommerce.cart_service.entity.Cart;
import com.ecommerce.cart_service.entity.CartItem;
import com.ecommerce.cart_service.event.ProductServiceEvent;
import com.ecommerce.cart_service.exception.AppException;
import com.ecommerce.cart_service.mapper.CartMapper;
import com.ecommerce.cart_service.repository.CartRepository;
import com.ecommerce.cart_service.service.CartService;
import com.ecommerce.cart_service.utils.CheckUserAccess;
import com.ecommerce.cart_service.utils.GetUserUtils;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    ProductServiceEvent productServiceEvent;
    CartMapper cartMapper;

    @Override
    @CheckUserAccess
    public CartResponse getCartByUser(String userId) {
        Cart cart = this.cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(),MessageError.CART_NOT_FOUND.getMessage()));
        return cartMapper.toResponse(cart);
    }

    @Override
    @CheckUserAccess
    public CartResponse addProductInCart( String userId, CartItemRequest cartItemRequest) {
        ProductResponse response;
        try {
            response = productServiceEvent.getProductDetail(cartItemRequest.getProductId()).getData();
        } catch (FeignException.NotFound e) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), MessageError.PRODUCT_NOT_FOUND.getMessage());
        }
        Cart cart = this.cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE).orElseGet(
                ()  -> {
                    Cart cartSub = new Cart();
                    cartSub.setUserId(userId);
                    cartSub.setStatus(CartStatus.ACTIVE);
                    List<CartItem>  cartItems = new ArrayList<>();
                    cartItems.add(CartItem.builder()
                            .productId(cartItemRequest.getProductId()).name(response.getName())
                            .price(cartItemRequest.getPrice()).quantity(cartItemRequest.getQuantity())
                            .cart(cartSub).subTotal(cartItemRequest.getPrice() * cartItemRequest.getQuantity())
                            .build());
                    cartSub.setCartItemList(cartItems);
                    cartSub.setTotal(cartItemRequest.getPrice() * cartItemRequest.getQuantity());
                    boolean isSuccess = this.productServiceEvent.updateProductFromCart(cartItemRequest.getProductId(), cartItemRequest.getQuantity(), false).getData();
//                    if(!isSuccess) throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "\n" +
//                            "Insufficient product quantity");
                    this.cartRepository.save(cartSub);
                    return cartSub;
                });

        List<CartItem> cartItems = cart.getCartItemList();
            cartItems.add(CartItem.builder()
                    .cart(cart).productId(cartItemRequest.getProductId())
                    .name(response.getName())
                    .quantity(cartItemRequest.getQuantity()).price(cartItemRequest.getPrice())
                    .subTotal(cartItemRequest.getQuantity() * cartItemRequest.getPrice())
                    .build());
            int totalPrice = cart.getCartItemList().stream().mapToInt(CartItem::getSubTotal).sum();
            cart.setTotal(totalPrice);
            cart.setCartItemList(cartItems);
            boolean isSuccess = this.productServiceEvent.updateProductFromCart(cartItemRequest.getProductId(), cartItemRequest.getQuantity(), false).getData();
            if(!isSuccess) throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "\n" +
                    "Insufficient product quantity");
            this.cartRepository.save(cart);

        return cartMapper.toResponse(cart);
    }

    @Override
    @CheckUserAccess
    public CartResponse updateProductInCart(String userId, CartItemRequest cartItemRequest) {
        Cart cart = this.cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND.value(),MessageError.CART_NOT_FOUND.getMessage())
        );
        CartItem cartItem = cart.getCartItemList().stream().filter(cItem -> cItem.getProductId().equals(cartItemRequest.getProductId())).findFirst()
                .orElseThrow(
                        () -> new AppException(HttpStatus.NOT_FOUND.value(),  MessageError.CART_ITEM_NOT_FOUND.getMessage())
                );
        if(cartItem.getQuantity() < cartItemRequest.getQuantity())
        {
            boolean isSuccess = this.productServiceEvent.updateProductFromCart(cartItemRequest.getProductId(), cartItemRequest.getQuantity(), false).getData();
            if(!isSuccess) throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "\n" +
                    "Insufficient product quantity");
        }
        else {
            this.productServiceEvent.updateProductFromCart(cartItemRequest.getProductId(), cartItemRequest.getQuantity(), true);
        }
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setSubTotal(cartItemRequest.getQuantity() * cartItem.getPrice());
            List<CartItem> cartItems = cart.getCartItemList();
            cartItems.stream().filter(cItem -> cItem.getProductId().equals(cartItemRequest.getProductId())).findFirst()
                            .map(cItem -> cartItem);
            cart.setCartItemList(cartItems);
            int totalPrice = cart.getCartItemList().stream().mapToInt(CartItem::getSubTotal).sum();
            cart.setTotal(totalPrice);

            this.cartRepository.save(cart);

        return cartMapper.toResponse(cart);
    }

    @Override
    @CheckUserAccess
    public boolean deleteProductInCart(String userId, String productId) {
        Cart cart = this.cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND.value(), MessageError.CART_NOT_FOUND.getMessage())
        );

        if(cart.getCartItemList().stream().filter(c -> c.getProductId().equals(productId)).findFirst().isEmpty())
            throw new AppException(HttpStatus.BAD_REQUEST.value(), MessageError.CART_ITEM_NOT_FOUND.getMessage());

        if(cart.getCartItemList().size() == 1){
                cart.setStatus(CartStatus.DELETED);
                this.cartRepository.save(cart);
                return true;
        }
        int quantity = cart.getCartItemList().stream().filter(c -> c.getProductId().equals(productId)).map(CartItem::getQuantity).findFirst().orElse(0);
        this.productServiceEvent.updateProductFromCart(productId, quantity, true);
        cart.getCartItemList().removeIf(item -> item.getProductId().equals(productId));
        int totalPrice = cart.getCartItemList().stream().mapToInt(CartItem::getSubTotal).sum();
        cart.setTotal(totalPrice);
        this.cartRepository.save(cart);
        return true;
    }

    @Override
    public void updateStatusCart(String userId, boolean check) {
        Cart cart;
        if(check){
            cart = this.cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE).orElseThrow(
                    () -> new AppException(HttpStatus.NOT_FOUND.value(), MessageError.CART_NOT_FOUND.getMessage())
            );
            cart.setStatus(CartStatus.PENDING_PAYMENT);
        }
        else{
            System.out.println("ADSDSAD");
            cart = this.cartRepository.findByUserIdAndStatus(userId, CartStatus.PENDING_PAYMENT).orElseThrow(
                    () -> new AppException(HttpStatus.NOT_FOUND.value(), MessageError.CART_NOT_FOUND.getMessage())
            );
            cart.setStatus(CartStatus.CHECK_OUT);
        }
        this.cartRepository.save(cart);
    }

    @Override
    @CheckUserAccess
    public void clearCart(String userId) {
        Cart cart = this.cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(
                        () -> new AppException(HttpStatus.NOT_FOUND.value(), MessageError.CART_NOT_FOUND.getMessage())
                );
        cart.setStatus(CartStatus.DELETED);
        for(CartItem cartItem: cart.getCartItemList()){
            this.productServiceEvent.updateProductFromCart(cartItem.getProductId(), cartItem.getQuantity(), true);
        }
        this.cartRepository.save(cart);
    }
}
