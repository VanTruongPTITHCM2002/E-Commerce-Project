package com.ecommerce.product_service.exception;

public class TooManyRequestException extends RuntimeException{
    public TooManyRequestException(String message) {
        super(message);
    }
}
