package com.ecommerce.cart_service.exception;


import com.ecommerce.cart_service.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<String>> handleAppException(AppException appException){
        return ResponseEntity.status(appException.getStatus())
                .body(ApiResponse.<String>builder()
                        .status(appException.getStatus())
                        .message(appException.getMessage())
                        .build());
    }
}
