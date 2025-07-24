package com.ecommerce.auth_service.controller;


import com.ecommerce.auth_service.common.ResponseMessageFailure;
import com.ecommerce.auth_service.common.ResponseMessageSuccess;
import com.ecommerce.auth_service.constants.RoleConstants;
import com.ecommerce.auth_service.dto.request.RoleRequest;
import com.ecommerce.auth_service.dto.response.ApiResponse;
import com.ecommerce.auth_service.dto.response.RoleResponse;
import com.ecommerce.auth_service.service.IRoleService;
import com.ecommerce.auth_service.utils.ResponseUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize(RoleConstants.ADMIN)
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getRoles (){
        List<RoleResponse> responses = this.iRoleService.getRoles();
        return ResponseUtils.ok(ResponseMessageSuccess.ROLES_LISTED.getMessage(), responses);
    }

    @GetMapping("/{roleId}")
    @PreAuthorize(RoleConstants.ADMIN)
    public ResponseEntity<ApiResponse<RoleResponse>> getRole(@PathVariable int roleId){
        RoleResponse response = this.iRoleService.getRole(roleId);
        return ResponseUtils.ok(ResponseMessageSuccess.ROLE_FOUND.getMessage(), response);
    }

    @PostMapping
    @PreAuthorize(RoleConstants.ADMIN)
    public ResponseEntity<ApiResponse<RoleResponse>> addRole (@RequestBody RoleRequest roleRequest){
        RoleResponse response = this.iRoleService.addRole(roleRequest);
        return ResponseUtils.create(ResponseMessageSuccess.ROLE_CREATED.getMessage(), response);

    }

    @PutMapping("/{roleId}")
    @PreAuthorize(RoleConstants.ADMIN)
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole (@PathVariable int roleId, @RequestBody RoleRequest roleRequest){
        RoleResponse response = this.iRoleService.updateRole(roleId,roleRequest);
        return ResponseUtils.ok(ResponseMessageSuccess.ROLE_UPDATED.getMessage(), response);
    }

    @DeleteMapping("/{roleId}")
    @PreAuthorize(RoleConstants.ADMIN)
    public ResponseEntity<ApiResponse<Boolean>> deleteRole(@PathVariable int roleId){
        boolean isDeleted = this.iRoleService.deleteRole(roleId);
        return ResponseUtils.ok(isDeleted ? ResponseMessageSuccess.ROLE_DELETED.getMessage() : ResponseMessageFailure.ROLE_DELETE_FAILED.getMessage(), isDeleted);
    }
}
