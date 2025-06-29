package com.ecommerce.auth_service.controller;

import com.ecommerce.auth_service.constants.RoleConstants;
import com.ecommerce.auth_service.dto.request.PermissionRequest;
import com.ecommerce.auth_service.dto.response.ApiResponse;
import com.ecommerce.auth_service.dto.response.PermissionResponse;
import com.ecommerce.auth_service.service.IPermissionService;
import com.ecommerce.auth_service.utils.ResponseUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PermissionController {

    IPermissionService iPermissionService;

    @GetMapping
    @PreAuthorize(RoleConstants.ADMIN)
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getPermissions(){
        List<PermissionResponse> responses = this.iPermissionService.getPermissions();
        return ResponseUtils.ok("Get permission successfully", responses);
    }

    @GetMapping("/{permissionId}")
    @PreAuthorize(RoleConstants.ADMIN)
    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionById (@PathVariable int permissionId){
        PermissionResponse permissionResponse = this.iPermissionService.getPermissionById(permissionId);
        return ResponseUtils.ok("Get permission successfully", permissionResponse);
    }

    @PostMapping
    @PreAuthorize(RoleConstants.ADMIN)
    public  ResponseEntity<ApiResponse<PermissionResponse>> addPPermission (@RequestBody PermissionRequest permissionRequest){
        PermissionResponse response = this.iPermissionService.addPermission(permissionRequest);
        return ResponseUtils.create("Created permission successfully", response);
    }

    @PutMapping("/{permissionId}")
    @PreAuthorize(RoleConstants.ADMIN)
    public ResponseEntity<ApiResponse<PermissionResponse>> updatePermission (@PathVariable int permissionId ,@RequestBody PermissionRequest permissionRequest){
        PermissionResponse response = this.iPermissionService.updatePermission(permissionId, permissionRequest);
        return ResponseUtils.ok("Update permission successfully", response);
    }

    @DeleteMapping("/{permissionId}")
    @PreAuthorize(RoleConstants.ADMIN)
    public ResponseEntity<ApiResponse<Boolean>> deletePermission (@PathVariable int permissionId){
        boolean isDeleted = this.iPermissionService.deletePermission(permissionId);
        return ResponseUtils.ok("Delete permission " + (isDeleted ? "successfully" : "failed"), isDeleted);
    }
}
