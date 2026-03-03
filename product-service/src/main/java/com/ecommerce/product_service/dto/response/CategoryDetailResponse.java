package com.ecommerce.product_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDetailResponse {
    UUID id;
    String name;
    String slug;
    String iconUrl;
    String description;
    UUID parentId;
    String parentName;
    ZonedDateTime createdAt;
    ZonedDateTime updatedAt;
}
