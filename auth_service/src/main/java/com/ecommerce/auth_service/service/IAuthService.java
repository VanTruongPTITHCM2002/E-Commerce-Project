package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.dto.request.AuthRequest;

public interface IAuthService {
    boolean loginPage(AuthRequest authRequest);
}
