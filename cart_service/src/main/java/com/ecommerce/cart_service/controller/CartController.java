package com.ecommerce.cart_service.controller;

import com.ecommerce.cart_service.dto.response.ApiResponse;
import com.ecommerce.cart_service.dto.response.CartResponse;
import com.ecommerce.cart_service.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<CartResponse>> getCartByUser (@PathVariable String userId){
        CartResponse cartResponse = this.cartService.getCartByUser(userId);
        return ResponseEntity.ok().body(ApiResponse.<CartResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get cart successfully")
                        .data(cartResponse)
                .build());
    }
}
