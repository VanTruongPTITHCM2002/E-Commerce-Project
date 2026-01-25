package com.ecommerce.product_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageSuccess {
    PRODUCT_CREATED_SUCCESSFULLY("Product created successfully."),
    PRODUCT_UPDATED_SUCCESSFULLY("Product updated successfully."),
    PRODUCT_DELETED_SUCCESSFULLY("Product deleted successfully."),
    PRODUCT_RETRIEVED_SUCCESSFULLY("Product retrieved successfully."),
    PRODUCT_LIST_RETRIEVED("Product list retrieved successfully.");
    private final String message;
}
