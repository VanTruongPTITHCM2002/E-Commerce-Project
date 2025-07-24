package com.ecommerce.auth_service.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordRequest {
    @NotBlank(message = "Current password must not be blank")
    String currentPassword;
    @NotBlank(message = "New password must not be blank")
    @Size(min = 4, message = "New password must be at least 4 characters")
    String newPassword;
    @NotBlank(message = "Confirm password must not be blank")
    String confirmPassword;
}
