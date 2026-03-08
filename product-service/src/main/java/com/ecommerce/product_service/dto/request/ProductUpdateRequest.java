package com.ecommerce.product_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUpdateRequest {
    @Size(min = 5, max = 100, message = "Product name must be at least 5 characters")
    @NotEmpty(message = "Product name must ne be empty")
    @NotBlank(message = "Product name must not be blank")
    @NotNull(message = "Product name must not be null")
    String name;
    @NotBlank(message = "Slug must not be blank")
    String slug;
    String thumbnail;
    List<String> images;
    BigDecimal rating;
    @Size(max = 5000, message = "Description is too long")
    String description;
    @Size(max = 1000, message = "Short description is too long")
    String shortDescription;
    Integer reviewCount;
    @NotEmpty(message = "Category id must not be empty")
    @NotNull(message = "Category id must not be null")
    String categoryId;
    @NotEmpty(message = "Brand id must not be empty")
    @NotNull(message = "Brand id must not be null")
    String brandId;
}
