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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getUsers (
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sorting", defaultValue = "username") String sorting
    ){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sorting).descending()); // in frontend must be page + 1;
        Page<UserResponse> userResponses = this.iUserService.getUsers(pageable);
        return ResponseUtils.ok("Get users successfully", userResponses);
    }

    @GetMapping("/{userId}")
    @PostAuthorize(RoleConstants.ADMIN + " || returnObject.body.data.username == authentication.name")
    public ResponseEntity<ApiResponse<UserResponse>> getUser (@PathVariable String userId){
        UserResponse userResponse = this.iUserService.getUser(userId);
        return ResponseUtils.ok("Get user successfully",userResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> addUser (@RequestBody UserRequest userRequest){
        UserResponse userResponse = this.iUserService.addUser(userRequest);
        return ResponseUtils.create("Created User Successfully", userResponse);
    }

    @PutMapping("/{userId}")
    @PostAuthorize(RoleConstants.ADMIN + " || returnObject.body.data.username == authentication.name")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser (@PathVariable String userId, @RequestBody UserRequest userRequest){
        UserResponse userResponse = this.iUserService.updateUser(userId, userRequest);
        return ResponseUtils.ok("Update user successfully", userResponse);
    }

    @DeleteMapping("/{userId}")
    @PostAuthorize(RoleConstants.ADMIN + " || returnObject.body.data.username == authentication.name")
    public ResponseEntity<ApiResponse<Boolean>> deleteUser (@PathVariable String userId){
        boolean isDeletedUser = this.iUserService.deleteUser(userId);
        return ResponseUtils.ok("Delete user " + (isDeletedUser ? "successfully" : "failed"), isDeletedUser);
    }
}
