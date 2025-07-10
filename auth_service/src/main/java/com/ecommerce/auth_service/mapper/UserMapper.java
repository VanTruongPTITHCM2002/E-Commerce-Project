package com.ecommerce.auth_service.mapper;

import com.ecommerce.auth_service.dto.request.UserRequest;
import com.ecommerce.auth_service.dto.response.UserResponse;
import com.ecommerce.auth_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<User, UserRequest, UserResponse> {


    @Override
    @Mapping(target = "role",ignore = true)
    User toEntity(UserRequest userRequest);

    @Override
    @Mapping(target = "role",source = "role.roleName")
    UserResponse toResponse(User user);

    @Override
    @Mapping(target = "role",ignore = true)
    void toUpdate(@MappingTarget User user, UserRequest userRequest);
}

