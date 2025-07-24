package com.ecommerce.auth_service.repository;

import com.ecommerce.auth_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Optional<Permission> findByPermissionName (String permissionName);
}
