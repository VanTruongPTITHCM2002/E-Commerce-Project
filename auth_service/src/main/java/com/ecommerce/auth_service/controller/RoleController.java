package com.ecommerce.auth_service.controller;


import com.ecommerce.auth_service.dto.request.RoleRequest;
import com.ecommerce.auth_service.dto.response.ApiResponse;
import com.ecommerce.auth_service.dto.response.RoleResponse;
import com.ecommerce.auth_service.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleController {

    IRoleService iRoleService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getRoles (){
        List<RoleResponse> responses = this.iRoleService.getRoles();
        return ResponseEntity.ok().body(
                ApiResponse.<List<RoleResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get Roles successfully")
                        .data(responses)
                        .build()
        );
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRole(@PathVariable int roleId){
        RoleResponse response = this.iRoleService.getRole(roleId);

        return ResponseEntity.ok().body(
                ApiResponse.<RoleResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get role successfully")
                        .data(response)
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> addRole (@RequestBody RoleRequest roleRequest){
        RoleResponse response = this.iRoleService.addRole(roleRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<RoleResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Created role successfully")
                        .data(response)
                        .build());
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole (@PathVariable int roleId, @RequestBody RoleRequest roleRequest){
        RoleResponse response = this.iRoleService.updateRole(roleId,roleRequest);
        return ResponseEntity.ok().body(
                ApiResponse.<RoleResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Update role successfully")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteRole(@PathVariable int roleId){
        boolean isDeleted = this.iRoleService.deleteRole(roleId);
        return ResponseEntity.ok().body(
                ApiResponse.<Boolean>builder()
                        .status(HttpStatus.OK.value())
                        .message("Delete role "+ (isDeleted ? "successfully" : "failed" ))
                        .data(isDeleted)
                        .build()
        );
    }
}
