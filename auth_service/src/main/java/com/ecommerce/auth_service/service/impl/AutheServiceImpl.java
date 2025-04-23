package com.ecommerce.auth_service.service.impl;

import com.ecommerce.auth_service.dto.request.AuthRequest;
import com.ecommerce.auth_service.entity.User;
import com.ecommerce.auth_service.repository.UserRepository;
import com.ecommerce.auth_service.service.IAuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class AutheServiceImpl implements IAuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public boolean loginPage(AuthRequest authRequest) {
        User user = this.userRepository.findByUsername(authRequest.getUsername()).orElse(null);

        return passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
    }
}
