package com.ecommerce.order_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemRequest {
    @NotNull(message = "Product must be not null")
    UUID productId;
    @NotNull(message = "Order must be not null")
    int orderId;
    @Min(value = 0, message = "Quantity must greater than zero")
    int quantity;
    @Min(value = 0, message = "Price must greater than zero")
    int price;
    @Min(value = 0, message = "subTotal must greater than zero")
    int subTotal;
}
