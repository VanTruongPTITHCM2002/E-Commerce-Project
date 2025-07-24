package com.ecommerce.order_service.Enum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum OrderStatus {
    CREATED ("Order has created"),
    PAID ("Order has paid"),
    PROCESSING ("Order have processing"),
    SHIPPED ("Order is shipping"),
    DELIVERED ("Order has delivered"),
    CANCELLED ("Order was cancelled"),
    FAILED("Order was failed");
    String name;

    OrderStatus (String name){
        this.name = name;
    }
}
