package com.ecommerce.auth_service.dto.request;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
