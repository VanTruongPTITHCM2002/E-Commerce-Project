package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.dto.request.AuthRequest;
import com.ecommerce.auth_service.dto.response.AuthResponse;
import com.nimbusds.jose.JOSEException;

public interface IAuthService {
    AuthResponse loginPage(AuthRequest authRequest) throws JOSEException;
}
