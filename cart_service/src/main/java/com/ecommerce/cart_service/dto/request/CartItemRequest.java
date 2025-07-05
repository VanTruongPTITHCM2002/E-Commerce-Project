package com.ecommerce.cart_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRequest {
    @NotEmpty(message = "productId is not empty")
    @NotNull(message = "productId is not null")
    String productId;
    @Min(value = 10000, message = "Price is greater than 10000")
    @NotEmpty(message = "Price is not empty")
    int price;
    @Min(value = 1, message = "Quantity is greater than 1")
    @NotEmpty(message = "Quantity is not empty")
    int quantity;
}
