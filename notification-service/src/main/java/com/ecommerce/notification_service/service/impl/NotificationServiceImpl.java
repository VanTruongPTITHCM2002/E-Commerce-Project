package com.ecommerce.notification_service.service.impl;

import com.ecommerce.notification_service.common.NotificationType;
import com.ecommerce.notification_service.dto.request.NotificationRequest;
import com.ecommerce.notification_service.entity.Notification;
import com.ecommerce.notification_service.repository.NotificationRepository;
import com.ecommerce.notification_service.send.NotificationSender;
import com.ecommerce.notification_service.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {

    List<NotificationSender> list;
    NotificationRepository notificationRepository;

    @Override
    public void send(NotificationRequest request) {
       if(request.getType().equals(NotificationType.MULTI))
           list.forEach(notification -> notification.send(request));
       else
           list.stream().filter(notification -> notification.getType().equals(request.getType()))
                   .findFirst().orElseThrow(
                           () -> new RuntimeException("Unsupported type")
                   ).send(request);

       this.notificationRepository.save(Notification.builder()
                       .userId(request.getUserId())
                       .title(request.getTitle())
                       .message(request.getMessage())
                       .sendAt(LocalDateTime.now())
                       .notificationType(request.getType())
                       .success(true)
               .build());
    }
}
