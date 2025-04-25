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
    public UserResponse add(UserRequest userRequest) {

        User user = this.userRepository.findByUsername(userRequest.getUsername()).orElseGet(() -> {
            User user1 = new User();
            user1 = userMapper.toUser(userRequest);
            user1.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user1.setRegisteredAt(LocalDateTime.now());
            user1.setRole(roleRepository.findByRoleName(userRequest.getRole()).orElseGet(() -> {
                    Role role = new Role();
                    role.setRoleName("USER");
                    role.setDescription("Quyen cua nguoi dung");
                    role.setCreateAt(LocalDateTime.now());
                    this.roleRepository.save(role);
                    return role;
            }));
            this.userRepository.save(user1);
            return  user1;
        });

        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        return List.of();
    }
}
