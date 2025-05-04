package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.dto.request.RoleRequest;
import com.ecommerce.auth_service.dto.response.RoleResponse;

import java.util.List;

public interface IRoleService {
    RoleResponse addRole (RoleRequest roleRequest);
    RoleResponse getRole (int roleId);
    List<RoleResponse> getRoles ();
    RoleResponse updateRole (int roleId, RoleRequest roleRequest);
    boolean deleteRole (int roleId);
}
