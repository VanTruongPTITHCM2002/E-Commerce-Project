package com.ecommerce.cart_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemRequest {
    @NotEmpty(message = "productId is not empty")
    @NotNull(message = "productId is not null")
    String productId;
    @Min(value = 10000, message = "Price is greater than 10000")
    @NotNull(message = "Price is not null")
    int price;
    @Min(value = 1, message = "Quantity is greater than 1")
    @NotNull(message = "Quantity is not null")
    int quantity;
}
