package com.ecommerce.auth_service.service.impl;

import com.ecommerce.auth_service.common.ResponseMessageFailure;
import com.ecommerce.auth_service.dto.request.PermissionRequest;
import com.ecommerce.auth_service.dto.response.PermissionResponse;
import com.ecommerce.auth_service.entity.Permission;
import com.ecommerce.auth_service.exception.AppException;
import com.ecommerce.auth_service.mapper.PermissionMapper;
import com.ecommerce.auth_service.repository.PermissionRepository;
import com.ecommerce.auth_service.service.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PermissionServiceImpl implements IPermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse addPermission(PermissionRequest permissionRequest) {
        Permission permission = this.permissionRepository.findByPermissionName(permissionRequest.getPermissionName()).orElse(null);

        if(permission != null){
            throw new AppException(HttpStatus.BAD_REQUEST.value(), ResponseMessageFailure.PERMISSION_ALREADY_EXISTS.getMessage());
        }

        permission = permissionMapper.toPermission(permissionRequest);
        permission.setCreateAt(LocalDateTime.now());
        this.permissionRepository.save(permission);

        return permissionMapper.toResponse(permission);
    }

    @Override
    public PermissionResponse getPermissionById(int permissionId) {
        Permission permission = this.permissionRepository.findById(permissionId).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND.value(), ResponseMessageFailure.PERMISSION_NOT_FOUND.getMessage())
        );
        return permissionMapper.toResponse(permission);
    }

    @Override
    public List<PermissionResponse> getPermissions() {
        return this.permissionRepository.findAll().stream()
                .map(permissionMapper::toResponse).toList();
    }

    @Override
    public PermissionResponse updatePermission(int permissionId, PermissionRequest permissionRequest) {
        Permission permission = this.permissionRepository.findById(permissionId).orElse(null);

        if(permission == null){
            throw  new AppException(HttpStatus.NOT_FOUND.value(), ResponseMessageFailure.PERMISSION_NOT_FOUND.getMessage());
        }

        permissionMapper.update(permission,permissionRequest);
        permission.setUpdateAt(LocalDateTime.now());
        this.permissionRepository.save(permission);

        return permissionMapper.toResponse(permission);
    }

    @Override
    public boolean deletePermission(int permissionId) {
        Permission permission = this.permissionRepository.findById(permissionId).orElse(null);
        if (permission == null){
            return false;
        }
        this.permissionRepository.delete(permission);
        return true;
    }
}
