package com.ecommerce.cart_service.service;

import com.ecommerce.cart_service.dto.response.CartResponse;

public interface CartService {
    CartResponse getCartByUser (String userId);
}
