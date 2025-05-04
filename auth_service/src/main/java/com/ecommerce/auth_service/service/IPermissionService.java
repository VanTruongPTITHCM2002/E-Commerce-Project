package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.dto.request.PermissionRequest;
import com.ecommerce.auth_service.dto.response.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse addPermission (PermissionRequest permissionRequest);
    PermissionResponse getPermissionById (int permissionId);
    List<PermissionResponse> getPermissions ();
    PermissionResponse updatePermission (int permissionId, PermissionRequest permissionRequest);
    boolean deletePermission (int permissionId);
}
