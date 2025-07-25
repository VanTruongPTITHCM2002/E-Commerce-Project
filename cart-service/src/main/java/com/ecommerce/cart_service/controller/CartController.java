package com.ecommerce.cart_service.controller;

import com.ecommerce.cart_service.common.MessageSuccess;
import com.ecommerce.cart_service.dto.request.CartItemRequest;
import com.ecommerce.cart_service.dto.response.ApiResponse;
import com.ecommerce.cart_service.dto.response.CartResponse;
import com.ecommerce.cart_service.service.CartService;
import com.ecommerce.cart_service.utils.ResponseUtils;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartService cartService;

    @GetMapping("/{userId}/cart")
//    @PostAuthorize("returnObject.body.data.userId == authentication.name")
    public ResponseEntity<ApiResponse<CartResponse>> getCartByUser (@PathVariable String userId){
        CartResponse cartResponse = this.cartService.getCartByUser(userId);
        return ResponseUtils.ok(MessageSuccess.CART_RETRIEVED.getMessage(), cartResponse);
    }

    @PostMapping("/{userId}/cart/items")
    public ResponseEntity<ApiResponse<CartResponse>> addProductInCart ( @PathVariable String userId
            , @RequestBody CartItemRequest cartItemRequest){
        CartResponse cartResponse = this.cartService.addProductInCart( userId, cartItemRequest);
        return ResponseUtils.create(MessageSuccess.CART_ITEM_ADDED.getMessage(), cartResponse);
    }

    @PutMapping("/{userId}/cart/items")
    public  ResponseEntity<ApiResponse<CartResponse>> updateProductInCart (@PathVariable String userId,
                                                                           @RequestBody @Valid CartItemRequest cartItemRequest){
        CartResponse cartResponse = this.cartService.updateProductInCart(userId, cartItemRequest);
        return  ResponseUtils.ok(MessageSuccess.CART_ITEM_UPDATED.getMessage(), cartResponse);
    }

    @PutMapping("/{userId}/cart")
    public ResponseEntity<ApiResponse<Void>> updateStatusCart (@PathVariable String userId,
                                                               @RequestParam boolean check
                                                               ){
        this.cartService.updateStatusCart(userId, check);
        return ResponseUtils.ok("Update status cart success");
    }

    @DeleteMapping("/{userId}/cart/items/{productId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteProductInCart (@PathVariable String userId,
                                                                     @PathVariable String productId){
        boolean isDelete = this.cartService.deleteProductInCart(userId, productId);
        return ResponseUtils.ok(MessageSuccess.CART_ITEM_REMOVED.getMessage(), isDelete);
    }

    @DeleteMapping("/{userId}/cart/items/clear")
    public ResponseEntity<ApiResponse<Boolean>> clearCart (@PathVariable String userId){
        this.cartService.clearCart(userId);
        return ResponseUtils.ok(MessageSuccess.CART_CLEARED.getMessage());
    }
}
