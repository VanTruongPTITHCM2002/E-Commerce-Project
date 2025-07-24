package com.ecommerce.auth_service.dto.request;

import com.ecommerce.auth_service.validation.NotBlankCustom;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthRequest {
    @Schema(description = "Username", example = "admin123", minLength = 4)
    @Size(min = 4,message = "Username at least 4 characters")
    @NotBlankCustom(message = "Username should not has blank")
    @NotNull(message = "Username shouldn't null")
    @NotEmpty(message = "Please don't ignore username")
    String username;

    @Schema(description = "Password", example = "********", minLength = 4)
    @Size(min = 4, message = "Password at least 4 characters")
    @NotEmpty(message = "Please don't ignore password")
    String password;
}
