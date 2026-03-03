package com.ecommerce.product_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantRequest {
    @NotBlank(message = "Product variant name must not be blank")
    @NotEmpty(message = "Product variant name must not be empty")
    @NotNull(message = "Product variant name must not be null")
    private String variantName;

    @NotBlank(message = "SKU must not be blank")
    @NotEmpty(message = "SKU must not be empty")
    @NotNull(message = "SKU must not be null")
    private String sku;

    @NotBlank(message = "Product id must not be blank")
    @NotEmpty(message = "Product id must not be empty")
    @NotNull(message = "Product id must not be null")
    private String productId;

    @NotNull(message = "Price must not be null")
    @NotEmpty(message = "Price must not be empty")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @DecimalMin(value = "0.0", message = "Original price must be greater than 0")
    private BigDecimal originalPrice;

    @DecimalMin(value = "0.0", message = "Cost price must be greater than 0")
    private BigDecimal costPrice;

    @NotNull(message = "Stock quantity must be not null")
    @Min(value = 0, message = "Stock quantity must be greater than or equal 0")
    private Integer stockQuantity;

    private JsonNode attributes;

    private List<String> images;
}
