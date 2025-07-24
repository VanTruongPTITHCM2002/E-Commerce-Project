package com.ecommerce.payment_service.utils;

import com.ecommerce.payment_service.dto.reponse.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static <T> ResponseEntity<ApiResponse<T>> ok ( String message){
        return ResponseEntity.ok().body(
                ApiResponse.<T>builder()
                        .status(HttpStatus.OK.value())
                        .message(message)
                        .build()
        );
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok ( String message, T data){
        return ResponseEntity.ok().body(
                ApiResponse.<T>builder()
                        .status(HttpStatus.OK.value())
                        .message(message)
                        .data(data)
                        .build()
        );
    }

    public static <T> ResponseEntity<ApiResponse<T>> created ( String message, T data){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<T>builder()
                        .status(HttpStatus.CREATED.value())
                        .message(message)
                        .data(data)
                        .build()
        );
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest ( String message){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.<T>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(message)
                        .build()
        );
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound ( String message){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.<T>builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(message)
                        .build()
        );
    }

    public static <T> ResponseEntity<ApiResponse<T>> internalServerError ( String message){
        return ResponseEntity.ok().body(
                ApiResponse.<T>builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(message)
                        .build()
        );
    }

    public static <T> ResponseEntity<ApiResponse<T>> forBidden ( String message){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ApiResponse.<T>builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .message(message)
                        .build()
        );
    }

    public static <T> ResponseEntity<ApiResponse<T>> unAuthorized ( String message){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ApiResponse.<T>builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message(message)
                        .build()
        );
    }
}
