package com.ecommerce.payment_service.service;

import com.ecommerce.payment_service.dto.reponse.PaymentResponse;
import com.ecommerce.payment_service.dto.reponse.PaymentURLResponse;
import com.ecommerce.payment_service.dto.request.PaymentRequest;

import java.util.Map;

public interface PaymentService {

    PaymentURLResponse createVnPayPayment(int orderId, String ipAddress);
    PaymentResponse addPayment (PaymentRequest paymentRequest);
    PaymentResponse getStatusPaymentOfOrder (int orderId);
    String handleVnpayReturn(Map<String, String> params);
}
