package com.ecommerce.auth_service.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException{

    private final int status;
    private String message;

    public AppException(int status, String message){
        super(message);
        this.status = status;
    }
}
