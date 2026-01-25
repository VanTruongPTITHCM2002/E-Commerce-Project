package com.ecommerce.product_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandRequest {
    @NotEmpty(message = "Brand name must not be empty")
    @NotBlank(message = "Brand name must not be blank")
    @NotNull(message = "Brand name must not be null")
    String name;
    @NotEmpty(message = "Slug must not be empty")
    @NotNull(message = "Slug must not be null")
    @NotBlank(message = "Slug must not be blank")
    String slug;
    @Size(max = 1000, message = "Logo Url is too long")
    String logoUrl;
    @Size(max = 1000, message = "Description is too long")
    String description;

    List<ProductRequest> products;
}
