package com.ecommerce.auth_service.controller;

import com.ecommerce.auth_service.common.ResponseMessageSuccess;
import com.ecommerce.auth_service.dto.request.AuthRequest;
import com.ecommerce.auth_service.dto.request.ChangePasswordRequest;
import com.ecommerce.auth_service.dto.request.RegisterRequest;
import com.ecommerce.auth_service.dto.request.TokenRequest;
import com.ecommerce.auth_service.dto.response.ApiResponse;
import com.ecommerce.auth_service.dto.response.AuthResponse;
import com.ecommerce.auth_service.dto.response.RegisterResponse;
import com.ecommerce.auth_service.dto.response.TokenResponse;
import com.ecommerce.auth_service.service.IAuthService;
import com.ecommerce.auth_service.utils.ResponseUtils;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Validated
@Slf4j
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
    public ResponseEntity<ApiResponse<AuthResponse>> login (@RequestBody @Valid AuthRequest authRequest) throws JOSEException {
        AuthResponse authResponse = this.iAuthService.loginPage(authRequest);
        return ResponseUtils.ok(ResponseMessageSuccess.LOGIN_SUCCESS.getMessage(), authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<TokenResponse>> logout (@RequestBody TokenRequest tokenRequest){
        TokenResponse tokenResponse = this.iAuthService.logoutPage(tokenRequest.getToken());
        return ResponseUtils.ok(ResponseMessageSuccess.LOGOUT_SUCCESS.getMessage(), tokenResponse);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<RegisterResponse>> signUp(@RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = this.iAuthService.registerPage(registerRequest);
        return ResponseUtils.create(ResponseMessageSuccess.REGISTER_SUCCESS.getMessage(), registerResponse);
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword (HttpServletRequest httpServletRequest, @RequestBody @Valid ChangePasswordRequest request){
        this.iAuthService.changePasswordPage(httpServletRequest,request);
        return ResponseUtils.unknown(HttpStatus.OK.value(), ResponseMessageSuccess.PASSWORD_CHANGED.getMessage());
    }
}
