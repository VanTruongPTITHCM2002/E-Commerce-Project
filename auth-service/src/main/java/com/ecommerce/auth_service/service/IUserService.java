package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.dto.request.UserRequest;
import com.ecommerce.auth_service.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IUserService {
    UserResponse addUser (UserRequest userRequest);
    UserResponse getUser (String userId);
    Page<UserResponse> getUsers(Pageable pageable, String firstName, String lastName, String phoneNumber, String email);
    UserResponse updateUser (String userId, UserRequest userRequest);
    boolean deleteUser (String userId);
}
