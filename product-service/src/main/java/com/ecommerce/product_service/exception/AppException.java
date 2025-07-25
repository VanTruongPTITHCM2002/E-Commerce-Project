package com.ecommerce.product_service.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException{

    private final int status;

    public AppException(int status, String message){
        super(message);
        this.status = status;
    }
}
