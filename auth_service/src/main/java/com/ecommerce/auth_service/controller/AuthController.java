package com.ecommerce.auth_service.controller;

import com.ecommerce.auth_service.dto.request.AuthRequest;
import com.ecommerce.auth_service.dto.response.ApiResponse;
import com.ecommerce.auth_service.service.IAuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class AuthController {

    IAuthService iAuthService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Boolean>> loginPage (@RequestBody AuthRequest authRequest){
        boolean isAuthorize = this.iAuthService.loginPage(authRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<Boolean>builder()
                        .status(HttpStatus.OK.value())
                        .data(isAuthorize)
                        .build()
        );

    }
}
