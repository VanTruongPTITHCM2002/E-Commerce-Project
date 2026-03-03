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
    PRODUCT_LIST_RETRIEVED("Product list retrieved successfully."),
    BRAND_LIST_RETRIEVED("Brand list retrieved successfully"),
    BRAND_CREATED_SUCCESSFULLY("Brand created successfully"),
    BRAND_UPDATED_SUCCESSFULLY("Brand updated successfully"),
    BRAND_DELETED_SUCCESSFULLY("Brand deleted successfully"),
    BRAND_RETRIEVED_SUCCESSFULLY("Brand retrieved successfully"),
    BRAND_SELECT_INFINITY_SUCCESSFULLY("Brand selected infinity successfully"),
    SLUG_LIST_RETRIEVED_SUCCESSFULLY("Slug list retrieved successfully"),
    BRAND_RETRIEVED_BY_CODE_SUCCESSFULLY("Brand retrieved by code successfully");
    private final String message;
}
