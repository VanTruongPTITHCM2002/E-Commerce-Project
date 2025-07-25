package com.ecommerce.product_service.exception;

import com.ecommerce.product_service.dto.response.ApiResponse;
import com.ecommerce.product_service.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNoResourceFoundException (NoResourceFoundException noResourceFoundException){
        return ResponseUtils.notFound("Not found method you access");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException (MethodArgumentNotValidException methodArgumentNotValidException) {
        String error = Objects.requireNonNull(methodArgumentNotValidException.getFieldError()).getDefaultMessage();
        return ResponseUtils.badRequest(error);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<String>> handleAppException (AppException appException){
        String error  = appException.getMessage();
        int status = appException.getStatus();
        return ResponseUtils.unknown(status, error);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleProductNotFoundException (ProductNotFoundException exception){
        String error = exception.getMessage();
        return ResponseUtils.notFound(error);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleProductAlreadyExistsException (ProductAlreadyExistsException e){
        String error = e.getMessage();
        return ResponseUtils.badRequest(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException (Exception e){
        return ResponseUtils.internalServerError(e.getMessage());
    }
}
