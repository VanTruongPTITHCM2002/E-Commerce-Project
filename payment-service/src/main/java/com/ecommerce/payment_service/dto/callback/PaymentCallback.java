package com.ecommerce.payment_service.dto.callback;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentCallback {
    String transactionId;
    String status;
}
