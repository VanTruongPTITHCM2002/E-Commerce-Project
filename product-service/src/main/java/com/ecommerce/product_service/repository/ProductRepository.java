package com.ecommerce.product_service.repository;

import com.ecommerce.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByName(String name);
    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE p.category.id = :id")
    List<Product> getProductsWithCategory (@Param("id") UUID categoryId);
}
