package com.ecommerce.auth_service.controller;

import com.ecommerce.auth_service.dto.request.AuthRequest;
import com.ecommerce.auth_service.dto.response.ApiResponse;
import com.ecommerce.auth_service.dto.response.AuthResponse;
import com.ecommerce.auth_service.service.IAuthService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Validated
@Tag(name = "Auth-service API",description = "Management Login, Log Out")
public class AuthController {

    IAuthService iAuthService;

    @PostMapping("/login")
    @Operation(summary = "Login")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Sign in success",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Username or password incorrect")
    })
    public ResponseEntity<ApiResponse<AuthResponse>> loginPage (@RequestBody @Valid AuthRequest authRequest) throws JOSEException {
        AuthResponse authResponse = this.iAuthService.loginPage(authRequest);
        return ResponseEntity.ok().body(
                ApiResponse.<AuthResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Login success")
                        .data(authResponse)
                        .build()

        );
    }
}
