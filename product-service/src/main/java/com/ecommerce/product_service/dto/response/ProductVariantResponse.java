package com.ecommerce.product_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVariantResponse {
    UUID id;
    String sku;
    String variantName;
    BigDecimal price;
    BigDecimal originalPrice;
    BigDecimal costPrice;
    BigDecimal stockQuantity;
    List<String> images;
    JsonNode attributes;
}
