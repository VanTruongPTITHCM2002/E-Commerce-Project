package com.ecommerce.product_service.exception;

import com.ecommerce.product_service.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNoResourceFoundException (NoResourceFoundException noResourceFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.<String>builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message("Not found method you access")
                        .build());
    }
}
