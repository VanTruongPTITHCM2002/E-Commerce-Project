package com.ecommerce.product_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageError {
    PRODUCT_NOT_FOUND("Product not found."),
    PRODUCT_CREATION_FAILED("Failed to create product."),
    PRODUCT_UPDATE_FAILED("Failed to update product."),
    PRODUCT_DELETE_FAILED("Cannot delete product. Product not found."),
    INVALID_PRODUCT_DATA("Invalid product data."),
    INTERNAL_SERVER_ERROR("An unexpected error occurred. Please try again later."),
    UNAUTHORIZED("Unauthorized access. Please log in."),
    METHOD_NOT_FOUND("Method access not found")
    ;
    private final String message;
}
