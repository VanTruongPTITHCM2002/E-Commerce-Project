package com.ecommerce.auth_service.service.impl;


import com.ecommerce.auth_service.common.ResponseMessageFailure;
import com.ecommerce.auth_service.dto.request.RoleRequest;
import com.ecommerce.auth_service.dto.response.RoleResponse;
import com.ecommerce.auth_service.entity.Role;
import com.ecommerce.auth_service.exception.AppException;
import com.ecommerce.auth_service.mapper.RoleMapper;
import com.ecommerce.auth_service.repository.PermissionRepository;
import com.ecommerce.auth_service.repository.RoleRepository;
import com.ecommerce.auth_service.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements IRoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse addRole(RoleRequest roleRequest) {
        Role role = this.roleRepository.findByRoleName(roleRequest.getRoleName()).orElseGet(() -> {
                    Role role1 = roleMapper.toRole(roleRequest);
                    role1.setCreateAt(LocalDateTime.now());
                    if(!roleRequest.getPermissions().isEmpty()){
                          role1.setPermissions(
                                               new HashSet<>(permissionRepository.findAll().stream()
                                .filter(permission -> roleRequest.getPermissions().contains(permission.getPermissionName()))
                                .toList())
                );
            }
                    this.roleRepository.save(role1);
                    return role1;
                }
        );

        return roleMapper.toResponse(role);
    }

    @Override
    public RoleResponse getRole(int roleId) {
        Role role = this.roleRepository.findById(roleId).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND.value(), ResponseMessageFailure.ROLE_NOT_FOUND.getMessage())
        );
        return roleMapper.toResponse(role);
    }

    @Override
    public List<RoleResponse> getRoles() {
        return this.roleRepository.findAll().stream().map(roleMapper::toResponse).toList();
    }

    @Override
    public RoleResponse updateRole(int roleId, RoleRequest roleRequest) {
        Role role = this.roleRepository.findById(roleId).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND.value(), ResponseMessageFailure.ROLE_NOT_FOUND.getMessage())
        );

        roleMapper.update(role, roleRequest);
        if (!roleRequest.getPermissions().isEmpty()) {
            role.setPermissions(
                    new HashSet<>(permissionRepository.findAll().stream()
                            .filter(permission -> roleRequest.getPermissions().contains(permission.getPermissionName()))
                            .toList())
            );
        }
        role.setUpdateAt(LocalDateTime.now());
        this.roleRepository.save(role);
        return roleMapper.toResponse(role);
    }
    @Override
    public boolean deleteRole(int roleId) {

        Role role = this.roleRepository.findById(roleId).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND.value(), ResponseMessageFailure.ROLE_NOT_FOUND.getMessage())
        );

        if(!role.getPermissions().isEmpty()){
           return false;
        }
        this.roleRepository.delete(role);
        return true;
    }
}
