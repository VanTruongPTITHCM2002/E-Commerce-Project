package com.ecommerce.product_service.repository;

import com.ecommerce.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByName(String name);
}
