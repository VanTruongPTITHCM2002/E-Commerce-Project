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
    BRAND_DELETE_FAILED("Cannot delete brand."),
    BRAND_NOT_FOUND("Brand not found."),
    BRAND_CODE_EXISTED("Brand code already existed."),
    BRAND_SLUG_EXISTED("Brand slug already existed."),
    INVALID_PRODUCT_DATA("Invalid product data."),
    INTERNAL_SERVER_ERROR("An unexpected error occurred. Please try again later."),
    UNAUTHORIZED("Unauthorized access. Please log in."),
    METHOD_NOT_FOUND("Method access not found"),
    STATUS_TRANSITION_ERROR("Cannot transition status"),
    CATEGORY_SLUG_EXISTED("Category slug already exists"),
    ;
    private final String message;
}
