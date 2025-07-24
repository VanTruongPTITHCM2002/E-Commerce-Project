package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.dto.request.AuthRequest;
import com.ecommerce.auth_service.dto.request.ChangePasswordRequest;
import com.ecommerce.auth_service.dto.request.RegisterRequest;
import com.ecommerce.auth_service.dto.response.AuthResponse;
import com.ecommerce.auth_service.dto.response.RegisterResponse;
import com.ecommerce.auth_service.dto.response.TokenResponse;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Principal;

public interface IAuthService {
    AuthResponse loginPage(AuthRequest authRequest) throws JOSEException;
    TokenResponse logoutPage ( String token);
    RegisterResponse registerPage (RegisterRequest registerRequest);
    void changePasswordPage (HttpServletRequest httpServletRequest, ChangePasswordRequest request);
}
