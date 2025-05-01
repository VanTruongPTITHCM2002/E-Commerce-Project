package com.ecommerce.auth_service.mapper;

import com.ecommerce.auth_service.dto.request.UserRequest;
import com.ecommerce.auth_service.dto.response.UserResponse;
import com.ecommerce.auth_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role",ignore = true)
    User toUser (UserRequest userRequest);

    @Mapping(target = "role",source = "role.roleName")
    UserResponse toUserResponse(User user);

}

