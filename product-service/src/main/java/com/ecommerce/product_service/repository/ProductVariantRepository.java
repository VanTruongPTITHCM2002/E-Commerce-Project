package com.ecommerce.product_service.repository;

import com.ecommerce.product_service.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID>, JpaSpecificationExecutor<ProductVariant> {
}