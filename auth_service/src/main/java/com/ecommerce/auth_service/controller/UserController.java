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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {
    IUserService iUserService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers (){
        List<UserResponse> responses = this.iUserService.getUsers();
        return ResponseEntity.ok()
                .body(
                        ApiResponse.<List<UserResponse>>builder()
                                .status(HttpStatus.OK.value())
                                .message("Get users successfully")
                                .data(responses)
                                .build()
                );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser (@PathVariable int userId){
        UserResponse userResponse = this.iUserService.getUser(userId);

        return ResponseEntity.ok().body(
                ApiResponse.<UserResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get user successfully")
                        .data(userResponse)
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> addUser (@RequestBody UserRequest userRequest){
            UserResponse userResponse = this.iUserService.addUser(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.<UserResponse>builder()
                            .status(HttpStatus.CREATED.value())
                            .message("Created User Successfully")
                            .data(userResponse)
                            .build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser (@PathVariable int userId, @RequestBody UserRequest userRequest){
        UserResponse userResponse = this.iUserService.updateUser(userId, userRequest);
        return ResponseEntity.ok().body(
                ApiResponse.<UserResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Update user successfully")
                        .data(userResponse)
                        .build()
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteUser (@PathVariable int userId){
        boolean isDeletedUser = this.iUserService.deleteUser(userId);

        return ResponseEntity.ok().body(
                ApiResponse.<Boolean>builder()
                        .status(HttpStatus.OK.value())
                        .message("Delete user " + (isDeletedUser ? "successfully" : "failed"))
                        .data(isDeletedUser)
                        .build()
        );
    }
}
