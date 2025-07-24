package com.ecommerce.cart_service.event;

import com.ecommerce.cart_service.dto.response.ApiResponse;
import com.ecommerce.cart_service.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:8080/api/v1/products")
public interface ProductServiceEvent {
    @GetMapping("/{productId}")
    ApiResponse<ProductResponse> getProductById(@PathVariable("productId") String productId);
}
