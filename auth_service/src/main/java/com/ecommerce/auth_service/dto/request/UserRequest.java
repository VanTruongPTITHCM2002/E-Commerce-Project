package com.ecommerce.auth_service.dto.request;


import com.ecommerce.auth_service.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String username;
    String password;
    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    LocalDateTime registeredAt;
    String role;
}
