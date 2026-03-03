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
public class CategoryRequest {
    @NotEmpty(message = "Category name must not be empty")
    @NotNull(message = "Category name must not be null")
    @NotBlank(message = "Category name must not be blank")
    @Size(min = 4, max = 100, message = "Category name must be at least 4 characters")
    String name;
    @NotEmpty(message = "Slug must not be empty")
    @NotNull(message = "Slug must not be null")
    @NotBlank(message = "Slug must not be blank")
    @Size(min = 4, max = 100, message = "Slug must be at least 4 characters")
    String slug;
    @Size(max = 1000, message = "Description is too long")
    String description;
    @Size(max = 1000, message = "Icon Url is too long")
    String iconUrl;
    String parentId;
    List<CategoryRequest> children;
}
