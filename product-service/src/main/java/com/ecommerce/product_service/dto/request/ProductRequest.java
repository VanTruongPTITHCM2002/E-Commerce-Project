package com.ecommerce.product_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;

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
    @Min(value = 0, message = "Cost Price must be equal greater than 0")
    int costPrice;
    String thumbnail;
    @Builder.Default
    double rating = 0.00;
    @Min(value = 0, message = "Quantity must be equal greater than 0")
    int quantity;
}
