package com.ecommerce.order_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    int orderId;
    String userId;
    LocalDate createAt;
    LocalDate updateAt;
    String status;
    List<OrderItemResponse> orderItemList;
    int totalPrice;
}
