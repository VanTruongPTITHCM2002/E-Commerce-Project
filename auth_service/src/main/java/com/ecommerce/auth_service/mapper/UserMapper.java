package com.ecommerce.auth_service.mapper;

import com.ecommerce.auth_service.dto.request.UserRequest;
import com.ecommerce.auth_service.dto.response.UserResponse;
import com.ecommerce.auth_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "role",ignore = true)
    User toUser (UserRequest userRequest);

    @Mapping(target = "role",source = "role.roleName")
    UserResponse toUserResponse(User user);

    @Mapping(target = "role",ignore = true)
    void updateUser(@MappingTarget User user, UserRequest userRequest);
}

