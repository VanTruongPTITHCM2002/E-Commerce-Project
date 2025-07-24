package com.ecommerce.order_service.event;

import com.ecommerce.order_service.dto.request.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "http://localhost:8086/api/v1/notifications")
public interface NotificationServiceClient {
    @PostMapping
    ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request);
}
