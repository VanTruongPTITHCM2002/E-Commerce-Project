package com.ecommerce.notification_service.send;

import com.ecommerce.notification_service.common.NotificationType;
import com.ecommerce.notification_service.dto.request.NotificationRequest;
import org.springframework.stereotype.Component;

@Component
public class EmailSender implements NotificationSender{
    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }

    @Override
    public void send(NotificationRequest request) {
        System.out.println("📧 Email to " + request.getUserId() + ": " + request.getTitle());
    }
}
