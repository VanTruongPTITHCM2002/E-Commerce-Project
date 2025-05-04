package com.ecommerce.auth_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.processing.Exclude;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    String username;
    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    LocalDateTime registeredAt;
    String role;
}
