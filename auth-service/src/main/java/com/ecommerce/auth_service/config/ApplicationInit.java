package com.ecommerce.auth_service.config;

import com.ecommerce.auth_service.entity.Role;
import com.ecommerce.auth_service.entity.User;
import com.ecommerce.auth_service.repository.RoleRepository;
import com.ecommerce.auth_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class ApplicationInit {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner (UserRepository userRepository, RoleRepository roleRepository){
        return args -> {

            Role role = roleRepository.findByRoleName("ADMIN").orElseGet(() ->{
                Role roleAdmin = new Role();
                roleAdmin.setRoleName("ADMIN");
                roleAdmin.setCreateAt(LocalDateTime.now());
                roleRepository.save(roleAdmin);
                return roleAdmin;
            });

            if(userRepository.findByUsername("admin").isEmpty()){
                User user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                user.setRegisteredAt(LocalDateTime.now());
                user.setRole(role);
                userRepository.save(user);
            }

        };
    }
}
