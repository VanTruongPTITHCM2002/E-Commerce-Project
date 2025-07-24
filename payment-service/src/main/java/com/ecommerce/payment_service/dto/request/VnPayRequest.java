package com.ecommerce.payment_service.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VnPayRequest {
    String orderId;
    int total;
    String ipAddress;
}
