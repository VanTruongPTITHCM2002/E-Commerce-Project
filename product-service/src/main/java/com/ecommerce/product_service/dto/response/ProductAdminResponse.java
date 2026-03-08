package com.ecommerce.product_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductAdminResponse {
    String id;
    String name;
    String slug;
    String thumbnail;
    String description;
    String shortDescription;
    List<String> images;
    String brandId;
    String brandName;
    String categoryId;
    String categoryName;
    ZonedDateTime createdAt;
    ZonedDateTime updatedAt;
    String entityStatus;
    boolean isDeleted;
}
