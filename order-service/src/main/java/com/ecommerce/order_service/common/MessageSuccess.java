package com.ecommerce.order_service.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageSuccess {
    ORDER_CREATED_SUCCESSFULLY("Order created successfully."),
    ORDER_CANCELLED_SUCCESSFULLY("Order cancelled successfully."),
    ORDER_UPDATED_SUCCESSFULLY("Order updated successfully."),
    ORDER_RETRIEVED_SUCCESSFULLY("Order retrieved successfully."),
    ORDER_LIST_RETRIEVED_SUCCESSFULLY("Order list retrieved successfully."),
    ORDER_STATUS_UPDATED_SUCCESSFULLY("Order status updated successfully.");

    private final String message;
}
