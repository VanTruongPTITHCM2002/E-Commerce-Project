package com.ecommerce.auth_service.service.impl;

import com.ecommerce.auth_service.common.ResponseMessageFailure;
import com.ecommerce.auth_service.dto.request.UserRequest;
import com.ecommerce.auth_service.dto.response.UserResponse;
import com.ecommerce.auth_service.entity.User;
import com.ecommerce.auth_service.exception.AppException;
import com.ecommerce.auth_service.mapper.UserMapper;
import com.ecommerce.auth_service.repository.RoleRepository;
import com.ecommerce.auth_service.repository.UserRepository;
import com.ecommerce.auth_service.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserServiceImpl implements IUserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse addUser(UserRequest userRequest) {

        User user = this.userRepository.findByUsername(userRequest.getUsername()).orElseGet(() -> {
            User user1 = new User();
            user1 = userMapper.toEntity(userRequest);
            user1.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user1.setRole(this.roleRepository.findByRoleName(userRequest.getRole()).orElse(null));
            this.userRepository.save(user1);
            return user1;
        });
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getUser(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND.value(), ResponseMessageFailure.USER_NOT_FOUND.getMessage())
        );
        return userMapper.toResponse(user);
    }

    @Override
    public Page<UserResponse> getUsers(Pageable pageable, String firstName, String lastName, String phoneNumber, String email) {
        Specification<User> specification = Specification.where(null);
        specification = specification.and(Optional.ofNullable(firstName).map(
                f -> (Specification<User>)(root, query, cb) -> cb.like(cb.lower(root.get("firstName")),f.toLowerCase().trim())
        ).orElse(null))
                        .and(Optional.ofNullable(lastName).map(
                                l -> (Specification<User>)(root, query, cb) -> cb.like(cb.lower(root.get("lastName")), l.toLowerCase().trim())).orElse(null))
                        .and(Optional.ofNullable(phoneNumber).map(
                                p -> (Specification<User>)(root, query, cb) -> cb.like(cb.lower(root.get("phoneNumber")), "%" + p + "%")).orElse(null))
                        .and(Optional.ofNullable(email).map(
                                e -> (Specification<User>)(root, query, cb) -> cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase().trim() + "%")
                        ).orElse(null)
        );

        Page<User> pages = this.userRepository.findAll(specification, pageable);
        return pages.map(this.userMapper::toResponse);
    }

    @Override
    @Transactional
    public UserResponse updateUser(String userId, UserRequest userRequest) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND.value(), ResponseMessageFailure.USER_NOT_FOUND.getMessage())
        );
        userMapper.toUpdate(user,userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        this.userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public boolean deleteUser(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND.value(), ResponseMessageFailure.USER_NOT_FOUND.getMessage())
        );
        user.setStatus(false);
        this.userRepository.save(user);
        return true;
    }
}
