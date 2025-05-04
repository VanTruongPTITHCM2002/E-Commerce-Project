package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.dto.request.UserRequest;
import com.ecommerce.auth_service.dto.response.UserResponse;

import java.util.List;

public interface IUserService {
    UserResponse addUser (UserRequest userRequest);
    UserResponse getUser (int userId);
    List<UserResponse> getUsers();
    UserResponse updateUser (int userId, UserRequest userRequest);
    boolean deleteUser (int userId);
}
