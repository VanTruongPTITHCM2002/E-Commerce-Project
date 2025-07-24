package com.ecommerce.order_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartResponse {
    int cartId;
    String userId;
    Date createAt;
    Date updateAt;
    String status;
    List<CartItemResponse> cartItemResponseList;
    int total;
}
