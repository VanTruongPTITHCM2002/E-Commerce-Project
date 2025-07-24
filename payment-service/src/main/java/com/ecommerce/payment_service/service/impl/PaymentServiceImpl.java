package com.ecommerce.payment_service.service.impl;

import com.ecommerce.order_service.Enum.OrderStatus;
import com.ecommerce.payment_service.client.VNPayClient;
import com.ecommerce.payment_service.common.PaymentMethod;
import com.ecommerce.payment_service.common.PaymentStatus;
import com.ecommerce.payment_service.dto.reponse.OrderResponse;
import com.ecommerce.payment_service.dto.reponse.PaymentResponse;
import com.ecommerce.payment_service.dto.reponse.PaymentURLResponse;
import com.ecommerce.payment_service.dto.request.PaymentRequest;
import com.ecommerce.payment_service.entity.Payment;
import com.ecommerce.payment_service.event.OrderServiceClient;
import com.ecommerce.payment_service.mapper.PaymentMapper;
import com.ecommerce.payment_service.repository.PaymentRepository;
import com.ecommerce.payment_service.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {

    OrderServiceClient orderServiceClient;
    VNPayClient vnPayClient;
    PaymentRepository paymentRepository;
    PaymentMapper paymentMapper;

    @Override
    public PaymentURLResponse createVnPayPayment(int orderId, String ipAddress) {

        OrderResponse response = this.orderServiceClient.getOrderByOrderId(orderId).getData();
        String transactionId = UUID.randomUUID().toString();

        this.paymentRepository.save(
                Payment.builder()
                        .transactionId(transactionId)
                        .orderId(orderId)
                        .total(response.getTotalPrice())
                        .status(PaymentStatus.PENDING)
                        .paymentMethod(PaymentMethod.VNPAY)
                        .createAt(LocalDateTime.now())
                        .build()
        );

        this.orderServiceClient.updateOrderStatus(orderId, String.valueOf(OrderStatus.PROCESSING));
        String redirectUrl = vnPayClient.createPaymentUrl(response, transactionId, ipAddress);
        return PaymentURLResponse.builder().redirectURL(redirectUrl).transactionId(transactionId).build();
    }

    public String handleVnpayReturn(Map<String, String> params) {
        if (!vnPayClient.verifyVnpayResponse(params)) return "Invalid signature";

        String txnRef = params.get("vnp_TxnRef");
        String responseCode = params.get("vnp_ResponseCode");

        Optional<Payment> opt = paymentRepository.findByTransactionId(txnRef);
        if (opt.isEmpty()) return "Transaction not found";

        Payment tx = opt.get();
        if ("00".equals(responseCode)) {
            tx.setStatus(PaymentStatus.SUCCESS);
            orderServiceClient.updateOrderStatus(tx.getOrderId(), String.valueOf(OrderStatus.PAID));
        } else {
            tx.setStatus(PaymentStatus.FAILED);
            orderServiceClient.updateOrderStatus(tx.getOrderId(), String.valueOf(OrderStatus.FAILED));
        }
        tx.setUpdateAt(LocalDateTime.now());
        paymentRepository.save(tx);

        return tx.getStatus() == PaymentStatus.SUCCESS ? "Payment Success" : "Payment Failed";
    }


    @Override
    public PaymentResponse addPayment(PaymentRequest paymentRequest) {
        Payment payment = paymentMapper.toEntity(paymentRequest);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreateAt(LocalDateTime.now());
        this.paymentRepository.save(payment);
        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse getStatusPaymentOfOrder(int orderId) {
        Payment payment = this.paymentRepository.findByOrderId(orderId).orElseThrow(
                () -> new RuntimeException("Not found order")
        );
        return paymentMapper.toResponse(payment);
    }
}
