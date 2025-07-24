package com.ecommerce.order_service.dto.request;

import com.ecommerce.order_service.Enum.NotificationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationRequest {
    String userId;
    String title;
    String message;
    NotificationType type;
}
