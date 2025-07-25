package com.ecommerce.order_service.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageError {
    ORDER_NOT_FOUND("Order not found."),
    ORDER_CREATION_FAILED("Failed to create order."),
    ORDER_CANCELLATION_FAILED("Failed to cancel order."),
    ORDER_UPDATE_FAILED("Failed to update order."),
    ORDER_STATUS_INVALID("Invalid order status."),
    UNAUTHORIZED_ORDER_ACCESS("You are not authorized to access this order."),
    ORDER_ALREADY_CANCELLED("Order has already been cancelled."),
    ORDER_CANNOT_BE_CANCELLED("Order cannot be cancelled at this stage."),
    ORDER_ALREADY_DELIVERED("Order has already been delivered."),
    INVALID_ORDER_ID("Invalid order ID."),
    PRODUCT_OUT_OF_STOCK("One or more products are out of stock."),
    PAYMENT_FAILED("Payment process failed.");

    private final String message;
}
