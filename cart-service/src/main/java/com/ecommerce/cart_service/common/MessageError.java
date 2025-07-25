package com.ecommerce.cart_service.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageError {
    UNAUTHORIZED("Unauthorized access. Please log in."),
    CART_NOT_FOUND("Cart not found"),
    CART_ITEM_NOT_FOUND("Cart item not found"),
    PRODUCT_ALREADY_IN_CART("Product is already in the cart"),
    PRODUCT_NOT_FOUND("Product not found"),
    INVALID_QUANTITY("Invalid product quantity"),
    UNAUTHORIZED_CART_ACCESS("You are not allowed to access this cart"),
    CHECKOUT_FAILED("Checkout process failed");
    private final String message;
}
