package com.ecommerce.auth_service.exception;

import com.ecommerce.auth_service.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Hidden
public class GlobalHandleException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String,String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException me){
        Map<String,String> map = new HashMap<>();
        me.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            map.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.<Map<String,String>>builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .data(map)
                                .build()
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException runtimeException){
        String error = runtimeException.getMessage();

        return ResponseEntity.badRequest().body(
                ApiResponse.<String>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(error)
                        .build()
        );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthorizeDeniedException(AuthorizationDeniedException authorizationDeniedException){
        String error = authorizationDeniedException.getMessage();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ApiResponse.<String>builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .message(error)
                        .build()
        );
    }
}
