package com.ecommerce.auth_service.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessageFailure {
    PASSWORD_INCORRECT("Password is incorrect"),
    USER_NOT_EXISTED("User not existed"),
    USER_EXISTED("User existed");
    private final String message;
}
