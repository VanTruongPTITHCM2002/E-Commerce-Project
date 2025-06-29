package com.ecommerce.cart_service.service.impl;

import com.ecommerce.cart_service.common.CartStatus;
import com.ecommerce.cart_service.dto.request.CartItemRequest;
import com.ecommerce.cart_service.dto.response.CartResponse;
import com.ecommerce.cart_service.entity.Cart;
import com.ecommerce.cart_service.entity.CartItem;
import com.ecommerce.cart_service.exception.AppException;
import com.ecommerce.cart_service.mapper.CartMapper;
import com.ecommerce.cart_service.repository.CartRepository;
import com.ecommerce.cart_service.service.CartService;
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

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    CartMapper cartMapper;

    @Override
    public CartResponse getCartByUser(String userId) {
        Cart cart = this.cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Not found cart"));
        return cartMapper.toResponse(cart);
    }

    @Override
    public CartResponse addProductInCart(String userId, CartItemRequest cartItemRequest) {
        Cart cart = this.cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE).orElseGet(
                ()  -> {
                    Cart cartSub = new Cart();
                    cartSub.setUserId(userId);
                    cartSub.setCreateAt(new Date());
                    cartSub.setStatus(CartStatus.ACTIVE);
                    List<CartItem>  cartItems = new ArrayList<>();
                    cartItems.add(CartItem.builder()
                            .productId(cartItemRequest.getProductId())
                            .price(cartItemRequest.getPrice())
                            .quantity(cartItemRequest.getQuantity())
                            .cart(cartSub)
                            .subTotal(cartItemRequest.getPrice() * cartItemRequest.getQuantity())
                            .build());
                    cartSub.setCartItemList(cartItems);
                    cartSub.setTotal(cartItemRequest.getPrice() * cartItemRequest.getQuantity());
                    this.cartRepository.save(cartSub);
                    return cartSub;
                });

        List<CartItem> cartItems = cart.getCartItemList();
        Optional<CartItem> existingCartItem =   cartItems.stream().filter(cartItem -> cartItem.getProductId().equals(cartItemRequest.getProductId())).findFirst();
        if(existingCartItem.isEmpty()){
            cartItems.add(CartItem.builder()
                    .cart(cart)
                    .productId(cartItemRequest.getProductId())
                    .quantity(cartItemRequest.getQuantity())
                    .price(cartItemRequest.getPrice())
                    .subTotal(cartItemRequest.getQuantity() * cartItemRequest.getQuantity())
                    .build());
            int totalPrice = cart.getCartItemList().stream().mapToInt(CartItem::getSubTotal).sum();
            cart.setTotal(totalPrice);
            cart.setCartItemList(cartItems);
            cart.setUpdateAt(new Date());
            this.cartRepository.save(cart);
        }
    else {
           throw  new AppException(HttpStatus.BAD_REQUEST.value(), "Product existed in cart");
        }
        return cartMapper.toResponse(cart);
    }

    @Override
    public CartResponse updateProductInCart(String userId, CartItemRequest cartItemRequest) {
        return null;
    }

    @Override
    public boolean deleteProductInCart(String userId, String productId) {
        Cart cart = this.cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND.value(), "Not found cart")
        );

        if(cart.getCartItemList().stream().filter(c -> c.getProductId().equals(productId)).findFirst().isEmpty())
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "Product wasn't existed in cart");

        if(cart.getCartItemList().size() == 1){
                cart.setStatus(CartStatus.EXPIRED);
                cart.setUpdateAt(new Date());
                this.cartRepository.save(cart);
                return true;
        }

        List<CartItem> cartItems  =cart.getCartItemList().stream()
                .filter(c -> !c.getProductId().equals(productId)).toList();

        cart.setCartItemList(cartItems);
        cart.setUpdateAt(new Date());
        this.cartRepository.save(cart);
        return true;
    }

    @Override
    public String clearCart(String userId) {
        Cart cart = this.cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(
                        () -> new AppException(HttpStatus.NOT_FOUND.value(), "Not found cart")
                );

        cart.setUpdateAt(new Date());
        cart.setStatus(CartStatus.EXPIRED);
        this.cartRepository.save(cart);
        return "Delete cart successfully";
    }
}
