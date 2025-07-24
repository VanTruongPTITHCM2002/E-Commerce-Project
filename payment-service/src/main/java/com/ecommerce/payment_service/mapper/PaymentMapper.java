package com.ecommerce.payment_service.mapper;

import com.ecommerce.payment_service.dto.reponse.PaymentResponse;
import com.ecommerce.payment_service.dto.request.PaymentRequest;
import com.ecommerce.payment_service.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity (PaymentRequest paymentRequest);
    PaymentResponse toResponse (Payment payment);
}
