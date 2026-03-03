package com.ecommerce.product_service.exception;

import com.ecommerce.product_service.dto.response.ApiResponse;
import com.ecommerce.product_service.dto.response.FieldErrorResponse;
import com.ecommerce.product_service.enums.MessageError;
import com.ecommerce.product_service.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNoResourceFoundException (NoResourceFoundException noResourceFoundException){
        return ResponseUtils.notFound(MessageError.METHOD_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<FieldErrorResponse>>> handleMethodArgumentNotValidException (MethodArgumentNotValidException methodArgumentNotValidException) {
        List<FieldErrorResponse> errors = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> FieldErrorResponse.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .rejectedValue(error.getRejectedValue())
                        .build())
                .toList();
        return ResponseUtils.badRequest(errors);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodNotAllowedException (MethodNotAllowedException methodNotAllowedException) {
        return ResponseUtils.unknown(HttpStatus.METHOD_NOT_ALLOWED.value(), methodNotAllowedException.getMessage());
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ApiResponse<String>> handleUnprocessableEntityException (UnprocessableEntityException unprocessableEntityException) {
        return  ResponseUtils.unknown(HttpStatus.UNPROCESSABLE_ENTITY.value(), unprocessableEntityException.getMessage());
    }

    @ExceptionHandler(TooManyRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleTooManyRequestException(TooManyRequestException tooManyRequestException) {
        return ResponseUtils.unknown(HttpStatus.TOO_MANY_REQUESTS.value(), tooManyRequestException.getMessage());
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex) {
        return ResponseUtils.badRequest("Request body must not be empty");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFoundException (NotFoundException notFoundException) {
        return ResponseUtils.notFound(notFoundException.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequestException (BadRequestException badRequestException) {
        return ResponseUtils.badRequest(badRequestException.getMessage());
    }
}
