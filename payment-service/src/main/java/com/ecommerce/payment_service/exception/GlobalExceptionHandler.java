package com.ecommerce.payment_service.exception;

import com.ecommerce.payment_service.dto.reponse.ApiResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.Unauthorized.class)
    public ResponseEntity<ApiResponse<String>> handleFeignExceptionUnauthorized(FeignException.Unauthorized unauthorized){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.<String>builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message("You must login")
                        .build());
    }
}
