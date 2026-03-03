package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.request.ProductVariantRequest;
import com.ecommerce.product_service.dto.request.ProductVariantUpdateRequest;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.dto.response.ProductVariantResponse;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.entity.ProductVariant;
import com.ecommerce.product_service.enums.EntityStatus;
import com.ecommerce.product_service.exception.ConflictException;
import com.ecommerce.product_service.mapper.ProductVariantMapper;
import com.ecommerce.product_service.repository.ProductVariantRepository;
import com.ecommerce.product_service.service.ProductService;
import com.ecommerce.product_service.service.ProductVariantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantServiceImpl implements ProductVariantService {
    ProductVariantRepository productVariantRepository;
    ProductService productService;
    ProductVariantMapper productVariantMapper;

    @Override
    @Transactional
    public ProductVariantResponse createProductVariant(ProductVariantRequest productVariantRequest) {
        this.checkExistsSku(productVariantRequest.getSku());
        this.checkExistsVariantName(productVariantRequest.getVariantName());
        Product product = this.productService.checkExistsByIdAndReturn(productVariantRequest.getProductId());
        ProductVariant productVariant = this.productVariantMapper.toEntity(productVariantRequest);
        productVariant.setProduct(product);
        this.productVariantRepository.save(productVariant);
        return this.productVariantMapper.toResponse(productVariant);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductVariantResponse> getProductVariants(Specification<ProductVariant> specification, Pageable pageable, String filter) {

        specification = specification.and((root, cb, query) ->
                root.get("entityStatus").in(List.of(EntityStatus.ACTIVE))
                );

        Page<ProductVariant> productVariantPage = this.productVariantRepository.findAll(specification, pageable);

        Page<ProductVariantResponse> productVariantResponsePage = productVariantPage.map(this.productVariantMapper::toResponse);

        return new PageResponse<>(productVariantResponsePage, "Product variant paginate and filter", filter);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductVariantResponse getProductVariantById(String productVariantId) {
        UUID id = UUID.fromString(productVariantId);

        ProductVariant productVariant = this.productVariantRepository.findById(id)
                .orElseThrow(
                        () -> new ConflictException("Product Variant with id: " + productVariantId + " not found")
                );

        return this.productVariantMapper.toResponse(productVariant);
    }

    @Override
    @Transactional
    public ProductVariantResponse updateProductVariant(String productVariantId, ProductVariantUpdateRequest updateRequest) {
        UUID id = UUID.fromString(productVariantId);
        ProductVariant productVariant = this.productVariantRepository.findById(id)
                .filter(pr -> EntityStatus.ACTIVE.equals(pr.getEntityStatus()))
                .orElseThrow(
                        () -> new ConflictException("Product Variant with id: " + productVariantId + " not found")
                );
        this.productVariantMapper.toUpdate(updateRequest, productVariant);
        this.productVariantRepository.save(productVariant);
        return this.productVariantMapper.toResponse(productVariant);
    }

    @Override
    @Transactional
    public void deleteProductVariant(String productVariantId) {
        UUID id = UUID.fromString(productVariantId);
        ProductVariant productVariant = this.productVariantRepository.findById(id)
                .filter(pr -> EntityStatus.ACTIVE.equals(pr.getEntityStatus()))
                .orElseThrow(
                        () -> new ConflictException("Product Variant with id: " + productVariantId + " not found")
                );
        productVariant.setEntityStatus(EntityStatus.INACTIVE);
        productVariant.setUpdatedAt(ZonedDateTime.now());
        productVariant.setUpdatedBy("system");
        productVariant.setDeletedAt(ZonedDateTime.now());
        productVariant.setDeletedBy(UUID.randomUUID().toString());
        this.productVariantRepository.save(productVariant);
    }

    private void checkExistsSku (String sku) {
        boolean hasExistsSku = this.productVariantRepository.existsBySku(sku);

        if (hasExistsSku) {
            throw new ConflictException("Sku was existed");
        }
    }

    private void checkExistsVariantName (String variantName) {
        boolean hasExistsVariantName = this.productVariantRepository.existsByVariantName(variantName);

        if (hasExistsVariantName) {
            throw  new ConflictException("Product variant name was existed");
        }
    }
}
