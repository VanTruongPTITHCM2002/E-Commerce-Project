package com.ecommerce.product_service.dto.request;

import com.ecommerce.product_service.validation.NotEmptyObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NotEmptyObject
public class ProductRequest {
    @Size(min = 5, max = 100, message = "Product name must be at least 5 characters")
    @NotEmpty(message = "Product name must ne be empty")
    @NotBlank(message = "Product name must not be blank")
    @NotNull(message = "Product name must not be null")
    String name;
    @NotBlank(message = "Slug must not be blank")
    String slug;
    String thumbnail;
    List<String> images;
    @Builder.Default
    double rating = 0.00;
    @Size(max = 5000, message = "Description is too long")
    String description;
    @Size(max = 1000, message = "Short description is too long")
    String shortDescription;
    @NotEmpty(message = "Category id must not be empty")
    @NotNull(message = "Category id must not be null")
    String categoryId;
    @NotEmpty(message = "Brand id must not be empty")
    @NotNull(message = "Brand id must not be null")
    String brandId;
//    @NotEmpty(message = "Product must have at least one variant")
//    @Valid
//    List<ProductVariantRequest> variants;
}
