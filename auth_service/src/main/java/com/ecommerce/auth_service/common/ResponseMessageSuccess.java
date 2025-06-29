package com.ecommerce.auth_service.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessageSuccess {
    LOGIN_SUCCESS("Login successfully");

    private final String message;
}
