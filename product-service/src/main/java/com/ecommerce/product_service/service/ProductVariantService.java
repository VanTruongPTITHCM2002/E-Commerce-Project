package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.request.ProductVariantRequest;
import com.ecommerce.product_service.dto.request.ProductVariantUpdateRequest;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.dto.response.ProductVariantResponse;
import com.ecommerce.product_service.entity.ProductVariant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ProductVariantService {
    ProductVariantResponse createProductVariant (ProductVariantRequest productVariantRequest);
    PageResponse<ProductVariantResponse> getProductVariants (
            Specification<ProductVariant> specification,
            Pageable pageable,
            String filter
    );
    ProductVariantResponse getProductVariantById (String productVariantId);
    ProductVariantResponse updateProductVariant (String productVariantId, ProductVariantUpdateRequest updateRequest);
    void deleteProductVariant (String productVariantId);
}
