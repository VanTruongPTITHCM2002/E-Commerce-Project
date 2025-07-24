package com.ecommerce.auth_service.repository;

import com.ecommerce.auth_service.entity.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    @Override
    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    Optional<User> findByUsername(@Param("username") String username);
    boolean existsByUsername (String username);
    boolean existsByEmail (String email);
}
