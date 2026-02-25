package com.ecommerce.product_service.repository;

import com.ecommerce.product_service.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID>, JpaSpecificationExecutor<Brand> {
    boolean  existsByCode (String code);
    boolean existsBySlug (String slug);
    List<Brand> findBySlugIgnoreCase(String slug);
    Optional<Brand> findByCode (String code);
}