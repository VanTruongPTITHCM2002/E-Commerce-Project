package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.dto.request.UserRequest;
import com.ecommerce.auth_service.dto.response.UserResponse;

import java.util.List;

public interface IUserService {
    UserResponse add (UserRequest userRequest);
    List<UserResponse> getAll ();
}
