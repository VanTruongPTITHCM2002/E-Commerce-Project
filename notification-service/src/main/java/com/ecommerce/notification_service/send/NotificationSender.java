package com.ecommerce.notification_service.send;

import com.ecommerce.notification_service.common.NotificationType;
import com.ecommerce.notification_service.dto.request.NotificationRequest;

public interface NotificationSender {
    NotificationType getType();
    void send(NotificationRequest request);
}
