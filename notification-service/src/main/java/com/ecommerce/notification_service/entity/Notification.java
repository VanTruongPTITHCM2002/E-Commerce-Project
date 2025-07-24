package com.ecommerce.notification_service.entity;

import com.ecommerce.notification_service.common.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification {

    @Id
    @GeneratedValue
    UUID notificationID;

    String userId;
    String title;
    String message;
    LocalDateTime sendAt;
    @Enumerated(EnumType.STRING)
    NotificationType notificationType;
    boolean success;
}
