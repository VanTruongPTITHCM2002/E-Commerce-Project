package com.ecommerce.product_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message ){
        super(message);
    }
}
