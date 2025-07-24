package com.ecommerce.auth_service.mapper;


import com.ecommerce.auth_service.dto.request.RoleRequest;
import com.ecommerce.auth_service.dto.response.PermissionResponse;
import com.ecommerce.auth_service.dto.response.RoleResponse;
import com.ecommerce.auth_service.entity.Permission;
import com.ecommerce.auth_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses = PermissionMapper.class)
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "users",ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    Role toRole (RoleRequest request);

    @Mapping(target = "permissions", source = "permissions")
    @Mapping(target = "roleId", source = "roleId")
    RoleResponse toResponse (Role role);

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    void update (@MappingTarget Role role, RoleRequest request);

//    default Set<PermissionResponse> mapPermissions(Set<Permission> permissions) {
//        if (permissions == null) return null;
//        return permissions.stream()
//                .map(PermissionMapper.)
//                .collect(Collectors.toSet());
//    }
}
