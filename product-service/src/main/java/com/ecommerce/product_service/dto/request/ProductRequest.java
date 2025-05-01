package com.ecommerce.product_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @Size(min = 5, message = "Product name must be at least 5 characters")
    @NotEmpty(message = "Please don't empty product name")
    String name;
    @Min(value = 0, message = "Price must be equal greater than 0")
    int price;
    @Min(value = 0, message = "Quantity must be equal greater than 0")
    int quantity;
}
