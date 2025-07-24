package com.ecommerce.order_service.event;

import com.ecommerce.order_service.dto.response.ApiResponse;
import com.ecommerce.order_service.dto.response.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cart-service", url = "http://localhost:8084/api/v1/")
public interface CartServiceClient {
    @GetMapping("/{userId}/cart")
    ApiResponse<CartResponse> getCartByUser (@PathVariable String userId);
    @PutMapping("/{userId}/cart")
    void updateStatusCart (@PathVariable String userId,
                                       @RequestParam boolean check
    );
}
