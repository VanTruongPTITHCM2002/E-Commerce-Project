package com.ecommerce.product_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantUpdateRequest {
    String productId;
    String sku;
    BigDecimal price;
    String variantName;
    BigDecimal originalPrice;
    BigDecimal costPrice;
    BigDecimal stockQuantity;
    List<String> images;
    JsonNode attributes;
}
