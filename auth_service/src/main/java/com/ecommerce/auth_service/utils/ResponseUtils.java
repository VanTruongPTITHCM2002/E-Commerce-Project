package com.ecommerce.auth_service.utils;

import com.ecommerce.auth_service.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {
    public static <T> ResponseEntity<ApiResponse<T>> ok (String message, T data){
        return ResponseEntity.ok().body(ApiResponse.<T>builder()
                        .status(HttpStatus.OK.value())
                        .message(message)
                        .data(data)
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> create (String message, T data){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(ApiResponse.<T>builder()
                .status(HttpStatus.CREATED.value())
                .message(message)
                .data(data)
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound (String message){
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(ApiResponse.<T>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(message)
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest (String message){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(ApiResponse.<T>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest( T data) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(ApiResponse.<T>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .data(data)
                        .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> forBidden (String message){
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(ApiResponse.<T>builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message(message)
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> internalServerError(String message){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(ApiResponse.<T>builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(message)
                        .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> unknown (int status, String message){
        return ResponseEntity.status(status)
                .body(ApiResponse.<T>builder()
                        .status(status)
                        .message(message)
                        .build());
    }
}
