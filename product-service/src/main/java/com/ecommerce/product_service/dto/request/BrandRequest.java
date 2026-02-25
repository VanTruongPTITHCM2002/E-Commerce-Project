package com.ecommerce.product_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
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
    @Size(max = 100, message = "Brand name must be at most 100 characters")
    String name;
    @NotEmpty(message = "Brand code must not be empty")
    @NotBlank(message = "Brand code must not be blank")
    @NotNull(message = "Brand code must not be null")
    @Size(max = 50, message = "Brand code must be at most 50 characters")
    @Pattern(
            regexp = "^[A-Z0-9_-]+$",
            message = "Brand code must contain only uppercase letters, numbers, _ or -"
    )
    String code;
    @NotEmpty(message = "Brand slug must not be empty")
    @NotNull(message = "Brand slug must not be null")
    @NotBlank(message = "Brand slug must not be blank")
    @Size(max = 100, message = "Brand slug must be at most 100 characters")
    @Pattern(
            regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$",
            message = "Brand slug must be lowercase, hyphen-separated (e.g. nike-air)"
    )
    String slug;
    @Size(max = 1000, message = "Logo url must be at most 1000 characters")
    String logoUrl;
    @Size(max = 1000, message = "Description must be at most 1000 characters")
    String description;

}
