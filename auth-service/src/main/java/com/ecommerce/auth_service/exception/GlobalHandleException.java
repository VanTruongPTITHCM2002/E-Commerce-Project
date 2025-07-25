package com.ecommerce.auth_service.exception;

import com.ecommerce.auth_service.dto.response.ApiResponse;
import com.ecommerce.auth_service.utils.ResponseUtils;
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
        return ResponseUtils.badRequest(map);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<String>> handleAppException(AppException appException){
        int status = appException.getStatus();
        String error = appException.getMessage();
        System.out.println(error);
        return ResponseUtils.unknown(status, error);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse<String>> handleNullPointerException(NullPointerException nullPointerException){
        return ResponseUtils.badRequest("Please check, value can null");
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthorizeDeniedException(AuthorizationDeniedException authorizationDeniedException){
        String error = authorizationDeniedException.getMessage();
        return ResponseUtils.forBidden(error);
    }
}
