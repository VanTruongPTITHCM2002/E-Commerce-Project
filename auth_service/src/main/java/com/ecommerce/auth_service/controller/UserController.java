package com.ecommerce.auth_service.controller;

import com.ecommerce.auth_service.constants.RoleConstants;
import com.ecommerce.auth_service.dto.request.UserRequest;
import com.ecommerce.auth_service.dto.response.ApiResponse;
import com.ecommerce.auth_service.dto.response.UserResponse;
import com.ecommerce.auth_service.service.IUserService;
import com.ecommerce.auth_service.utils.ResponseUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {

    IUserService iUserService;

    @GetMapping
    @PreAuthorize(RoleConstants.ADMIN)
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers (){
        List<UserResponse> responses = this.iUserService.getUsers();
        return ResponseUtils.ok("Get users successfully", responses);
    }

    @GetMapping("/{userId}")
    @PostAuthorize(RoleConstants.ADMIN + " || returnObject.body.data.username == authentication.name")
    public ResponseEntity<ApiResponse<UserResponse>> getUser (@PathVariable int userId){
        UserResponse userResponse = this.iUserService.getUser(userId);
        return ResponseUtils.ok("Get user successfully",userResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> addUser (@RequestBody UserRequest userRequest){
        UserResponse userResponse = this.iUserService.addUser(userRequest);
        return ResponseUtils.create("Created User Successfully", userResponse);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser (@PathVariable int userId, @RequestBody UserRequest userRequest){
        UserResponse userResponse = this.iUserService.updateUser(userId, userRequest);
        return ResponseUtils.ok("Update user successfully", userResponse);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteUser (@PathVariable int userId){
        boolean isDeletedUser = this.iUserService.deleteUser(userId);
        return ResponseUtils.ok("Delete user " + (isDeletedUser ? "successfully" : "failed"), isDeletedUser);
    }
}
