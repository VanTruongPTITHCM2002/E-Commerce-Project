package com.ecommerce.product_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageSuccess {
    PRODUCT_CREATED_SUCCESSFULLY("Product created successfully."),
    PRODUCT_CREATED_BULK_SUCCESSFULLY("Product created bulk successfully."),
    PRODUCT_UPDATED_SUCCESSFULLY("Product updated successfully."),
    PRODUCT_UPDATED_BULK_FAILURE("Product updated bulk failure."),
    PRODUCT_DELETED_SUCCESSFULLY("Product deleted successfully."),
    PRODUCT_DELETED_BULK_SUCCESSFULLY("Product deleted bulk successfully."),
    PRODUCT_RETRIEVED_SUCCESSFULLY("Product retrieved successfully."),
    PRODUCT_LIST_RETRIEVED("Product list retrieved successfully."),
    CATEGORY_CREATED_SUCCESSFULLY("Category created successfully."),
    CATEGORY_LIST_RETRIEVED("Category list retrieved successfully."),
    CATEGORY_RETRIEVED_SUCCESSFULLY("Category retrieved successfully."),
    CATEGORY_VIEW_TREE_SUCCESSFULLY("Category view tree successfully."),
    CATEGORY_VIEW_TREES_SUCCESSFULLY("Category view trees successfully."),
    CATEGORY_VIEW_ROOT_SUCCESSFULLY("Category view root successfully."),
    CATEGORY_UPDATED_SUCCESSFULLY("Category updated successfully."),
    CATEGORY_DELETED_SUCCESSFULLY("Category deleted successfully."),
    STATUS_TRASITION_SUCCESSFULLY("Status transition successfully."),
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
