package com.ecommerce.product_service.repository;

import com.ecommerce.product_service.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID>, JpaSpecificationExecutor<ProductVariant> {
    boolean existsBySku (String sku);
    boolean existsByVariantName (String variantName);
    @Query("SELECT pv from ProductVariant pv where pv.product.id = :productId")
    List<ProductVariant> findByProduct (UUID productId);
}