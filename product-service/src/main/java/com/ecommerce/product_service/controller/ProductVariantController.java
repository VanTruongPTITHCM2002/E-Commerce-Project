package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.request.ProductVariantRequest;
import com.ecommerce.product_service.dto.request.ProductVariantUpdateRequest;
import com.ecommerce.product_service.dto.response.ApiResponse;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.dto.response.ProductVariantResponse;
import com.ecommerce.product_service.entity.ProductVariant;
import com.ecommerce.product_service.enums.MessageSuccess;
import com.ecommerce.product_service.service.ProductVariantService;
import com.ecommerce.product_service.utils.ResponseUtils;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product-variants")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantController {
    ProductVariantService productVariantService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductVariantResponse>> createProductVariant (@RequestBody @Valid ProductVariantRequest request) {
        ProductVariantResponse response = this.productVariantService.createProductVariant(request);
        return ResponseUtils.create("Product variant created successfully", response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductVariantResponse>>> getProductVariantPagingAndFilter (
            @Filter Specification<ProductVariant> specification,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "filter", defaultValue = "null") String filter
            ) {
        PageResponse<ProductVariantResponse> response = this.productVariantService.getProductVariants(specification, pageable, filter);
        return ResponseUtils.ok("Product variants list retrieved successfully", response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductVariantResponse>> getProductVariantById (@PathVariable("id") String id) {
        ProductVariantResponse response = this.productVariantService.getProductVariantById(id);
        return ResponseUtils.ok("Product variant retrieved successfully", response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductVariantResponse>> updateProductVariantById (@PathVariable("id") String id, @RequestBody @Valid ProductVariantUpdateRequest updateRequest) {
        ProductVariantResponse response = this.productVariantService.updateProductVariant(id, updateRequest);
        return ResponseUtils.ok("Product variant updated successfully", response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductVariantById (@PathVariable("id") String id) {
        this.productVariantService.deleteProductVariant(id);
        return ResponseUtils.ok("Product variant deleted successfully", null);
    }
}
