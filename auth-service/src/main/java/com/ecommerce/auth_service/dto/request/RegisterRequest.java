package com.ecommerce.auth_service.dto.request;

import com.ecommerce.auth_service.validation.NotBlankCustom;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    @Size(min = 4,message = "Username at least 4 characters")
    @NotBlankCustom(message = "Username should not has blank")
    @NotNull(message = "Username shouldn't null")
    @NotEmpty(message = "Please don't ignore username")
    String username;
    @Size(min = 4, message = "Password at least 4 characters")
    @NotEmpty(message = "Please don't ignore password")
    @NotBlankCustom(message = "Password should not has blank")
    String password;
    @Email(message = "Please fill format email @")
    @NotEmpty(message = "Please don't ignore email")
    String email;
    String role;
}
