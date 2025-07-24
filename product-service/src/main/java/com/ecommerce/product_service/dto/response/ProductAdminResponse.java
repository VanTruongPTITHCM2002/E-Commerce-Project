package com.ecommerce.product_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductAdminResponse {
    String productId;
    String name;
    String thumbnail;
    int price;
    int costPrice;
    int quantity;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    boolean isDeleted;
}
