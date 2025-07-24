package com.ecommerce.payment_service.event;

import com.ecommerce.order_service.Enum.OrderStatus;
import com.ecommerce.payment_service.dto.reponse.ApiResponse;
import com.ecommerce.payment_service.dto.reponse.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name ="order-service" ,url = "http://localhost:8082/api/v1")
public interface OrderServiceClient {
    @GetMapping("/orders/{orderId}")
    ApiResponse<OrderResponse> getOrderByOrderId(@PathVariable int orderId);

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<ApiResponse<String>> updateOrderStatus (@PathVariable int orderId
            , @RequestParam String orderStatus);
}
