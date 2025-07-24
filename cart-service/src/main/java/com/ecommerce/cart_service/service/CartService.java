package com.ecommerce.cart_service.service;

import com.ecommerce.cart_service.dto.request.CartItemRequest;
import com.ecommerce.cart_service.dto.response.CartResponse;

public interface CartService {
    CartResponse getCartByUser (String userId);
    CartResponse addProductInCart (String userId, CartItemRequest cartItemRequest);
    CartResponse updateProductInCart(String userId,  CartItemRequest cartItemRequest);
    boolean  deleteProductInCart (String userId, String productId);
    void updateStatusCart(String userId,boolean check);
    String clearCart (String userId);
}
