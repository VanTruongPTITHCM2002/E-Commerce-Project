package com.ecommerce.product_service.utils;

import com.ecommerce.product_service.response.ApiResponse;

public class ResponseUtil {
    public static <T> ApiResponse<T> success(int statusCode, String message, T data){
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(statusCode);
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        return apiResponse;
    }

    public static <T> ApiResponse<T> error (int statusCode, String message, T data){
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(statusCode);
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        return apiResponse;
    }
}
