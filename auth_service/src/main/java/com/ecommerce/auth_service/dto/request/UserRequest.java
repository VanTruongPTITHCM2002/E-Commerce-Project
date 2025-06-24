package com.ecommerce.auth_service.dto.request;



import com.ecommerce.auth_service.validation.NotBlankCustom;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
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
    @Size(min = 4, message = "Username at least 4 characters")
    @NotBlankCustom(message = "Username shouldn't has blank")
    @NotNull(message = "Username shouldn't null")
    @NotEmpty(message = "Please don't ignore username")
    String username;
    @Size(min = 4, message = "Password at least 4 characters")
    @NotEmpty(message = "Please don't ignore password")
    String password;
    String firstName;
    String lastName;
    String phoneNumber;
    @Pattern(regexp = "^(.+)@(\\S+)$", message = "Email is not format")
    String email;
    LocalDateTime registeredAt;
    String role;
    @Builder.Default
    boolean status = true;
}
