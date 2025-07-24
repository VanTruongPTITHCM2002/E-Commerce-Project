package com.ecommerce.notification_service.service;

import com.ecommerce.notification_service.dto.request.NotificationRequest;

public interface NotificationService {
    void send(NotificationRequest request);
}
