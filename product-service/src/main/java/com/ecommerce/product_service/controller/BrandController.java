package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.request.BrandRequest;
import com.ecommerce.product_service.dto.response.ApiResponse;
import com.ecommerce.product_service.dto.response.BrandResponse;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.entity.Brand;
import com.ecommerce.product_service.service.BrandService;
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
@RequestMapping("/api/v1/brands")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {
    BrandService brandService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BrandResponse>>> getBrandsPaginate (
            @Filter Specification<Brand> specification,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable,
            @RequestParam(value = "filter", defaultValue = "null") String filter
            ) {
            PageResponse<BrandResponse> responses = this.brandService.getBrands(specification, pageable, filter);
            return ResponseUtils.ok("Get brands paginate successfully",responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandById (@PathVariable("id") String id) {
        BrandResponse response = this.brandService.getBrandById(id);
        return ResponseUtils.ok("Get brand with id: " + id + " successfully", response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BrandResponse>> createBrand (@RequestBody @Valid BrandRequest brandRequest) {
        BrandResponse brandResponse = this.brandService.createBrand(brandRequest);
        return ResponseUtils.ok("Create brand successfully", brandResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand (@PathVariable("id") String id) {
        this.brandService.deleteBrand(id);
        return ResponseUtils.ok("Delete brand successfully", null);
    }
}
