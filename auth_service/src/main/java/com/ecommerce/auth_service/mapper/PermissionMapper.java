package com.ecommerce.auth_service.mapper;

import com.ecommerce.auth_service.dto.request.PermissionRequest;
import com.ecommerce.auth_service.dto.response.PermissionResponse;
import com.ecommerce.auth_service.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    @Mapping(target = "createAt",ignore = true)
    Permission toPermission(PermissionRequest permissionRequest);

    @Mapping(target = "permissionId", source = "permissionId")
    PermissionResponse toResponse(Permission permission);

    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "createAt",ignore = true)
    void update(@MappingTarget Permission permission, PermissionRequest permissionRequest);
}
