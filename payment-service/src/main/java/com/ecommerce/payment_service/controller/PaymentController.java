package com.ecommerce.payment_service.controller;

import com.ecommerce.payment_service.dto.reponse.ApiResponse;
import com.ecommerce.payment_service.dto.reponse.PaymentResponse;
import com.ecommerce.payment_service.dto.reponse.PaymentURLResponse;
import com.ecommerce.payment_service.dto.request.PaymentRequest;
import com.ecommerce.payment_service.service.PaymentService;
import com.ecommerce.payment_service.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    PaymentService paymentService;


    @PostMapping("/vnpay/create")
    public ResponseEntity<PaymentURLResponse> createVnpayPayment(@RequestParam int orderId,
                                                                 HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        PaymentURLResponse response = paymentService.createVnPayPayment(orderId, ip);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vnpay/return")
    public ResponseEntity<String> handleVnpayReturn(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(paymentService.handleVnpayReturn(params));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> addPayment (@RequestBody PaymentRequest paymentRequest){
        PaymentResponse paymentResponse = this.paymentService.addPayment(paymentRequest);
        return ResponseUtils.created("Add payment successfully", paymentResponse);
    }

    @GetMapping("/status/{orderId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getStatusPaymentOfOrder (@PathVariable int orderId){
        PaymentResponse paymentResponse = this.paymentService.getStatusPaymentOfOrder(orderId);
        return ResponseUtils.ok("Get status payment successfully", paymentResponse);
    }
}
