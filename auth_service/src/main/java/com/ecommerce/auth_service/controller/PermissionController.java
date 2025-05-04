package com.ecommerce.auth_service.controller;

import com.ecommerce.auth_service.dto.request.PermissionRequest;
import com.ecommerce.auth_service.dto.response.ApiResponse;
import com.ecommerce.auth_service.dto.response.PermissionResponse;
import com.ecommerce.auth_service.service.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PermissionController {

    IPermissionService iPermissionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getPermissions(){
        List<PermissionResponse> responses = this.iPermissionService.getPermissions();

        return ResponseEntity.ok().body(
                ApiResponse.<List<PermissionResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get permissions successfully")
                        .data(responses)
                        .build()
        );
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionById (@PathVariable int permissionId){
        PermissionResponse permissionResponse = this.iPermissionService.getPermissionById(permissionId);
        return ResponseEntity.ok().body(
                ApiResponse.<PermissionResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get permission successfully")
                        .data(permissionResponse)
                        .build()
        );
    }

    @PostMapping
    public  ResponseEntity<ApiResponse<PermissionResponse>> addPPermission (@RequestBody PermissionRequest permissionRequest){
        PermissionResponse response = this.iPermissionService.addPermission(permissionRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PermissionResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Created permission successfully")
                        .data(response)
                        .build());
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<ApiResponse<PermissionResponse>> updatePermission (@PathVariable int permissionId ,@RequestBody PermissionRequest permissionRequest){
        PermissionResponse response = this.iPermissionService.updatePermission(permissionId, permissionRequest);

        return ResponseEntity.ok().body(
                ApiResponse.<PermissionResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Update permission successfully")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<ApiResponse<Boolean>> deletePermission (@PathVariable int permissionId){
        boolean isDeleted = this.iPermissionService.deletePermission(permissionId);

        return ResponseEntity.ok().body(
                ApiResponse.<Boolean>builder()
                        .status(HttpStatus.OK.value())
                        .message("Delete permission " + (isDeleted ? "successfully" : "failed"))
                        .data(isDeleted)
                        .build()
        );
    }
}
