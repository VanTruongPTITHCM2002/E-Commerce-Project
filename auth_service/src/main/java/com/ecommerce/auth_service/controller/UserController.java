package com.ecommerce.auth_service.controller;


import com.ecommerce.auth_service.dto.request.UserRequest;
import com.ecommerce.auth_service.dto.response.ApiResponse;
import com.ecommerce.auth_service.dto.response.UserResponse;
import com.ecommerce.auth_service.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {
    IUserService iUserService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> addUser (@RequestBody UserRequest userRequest){
            UserResponse userResponse = this.iUserService.add(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.<UserResponse>builder()
                            .status(HttpStatus.CREATED.value())
                            .message("Created User Successfully")
                            .data(userResponse)
                            .build());
    }
}
