package com.ecommerce.order_service.exception;

import com.ecommerce.order_service.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<String>> handleAppException (AppException appException){
        return ResponseEntity.status(appException.getStatus())
                .body(ApiResponse.<String>builder()
                        .status(appException.getStatus())
                        .message(appException.getMessage())
                        .build());
    }
}
