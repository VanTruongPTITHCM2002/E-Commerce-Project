package com.ecommerce.cart_service.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageSuccess {
    CART_ITEM_ADDED("Product added to cart successfully"),
    CART_ITEM_UPDATED("Cart item updated successfully"),
    CART_ITEM_REMOVED("Product removed from cart successfully"),
    CART_CLEARED("Cart cleared successfully"),
    CART_RETRIEVED("Cart retrieved successfully"),
    CHECKOUT_SUCCESS("Checkout completed successfully");
    private final String message;
}
