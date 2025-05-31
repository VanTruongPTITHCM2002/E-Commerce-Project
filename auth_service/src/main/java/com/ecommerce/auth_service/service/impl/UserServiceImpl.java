package com.ecommerce.auth_service.service.impl;

import com.ecommerce.auth_service.dto.request.UserRequest;
import com.ecommerce.auth_service.dto.response.UserResponse;
import com.ecommerce.auth_service.entity.Role;
import com.ecommerce.auth_service.entity.User;
import com.ecommerce.auth_service.mapper.UserMapper;
import com.ecommerce.auth_service.repository.RoleRepository;
import com.ecommerce.auth_service.repository.UserRepository;
import com.ecommerce.auth_service.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserServiceImpl implements IUserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse addUser(UserRequest userRequest) {

        User user = this.userRepository.findByUsername(userRequest.getUsername()).orElseGet(() -> {
            User user1 = new User();
            user1 = userMapper.toUser(userRequest);
            user1.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user1.setRegisteredAt(LocalDateTime.now());
            user1.setRole(this.roleRepository.findByRoleName(userRequest.getRole()).orElse(null));
            this.userRepository.save(user1);
            return  user1;
        });

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUser(int userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsers() {
        return this.userRepository.findAll()
                .stream()
                .filter(User::getStatus)
                .map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse updateUser(int userId, UserRequest userRequest) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        userMapper.updateUser(user,userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        this.userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public boolean deleteUser(int userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        user.setStatus(false);
        this.userRepository.save(user);
        return true;
    }
}
