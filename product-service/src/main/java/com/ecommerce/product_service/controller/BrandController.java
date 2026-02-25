package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.request.BrandRequest;
import com.ecommerce.product_service.dto.request.BrandUpdateRequest;
import com.ecommerce.product_service.dto.response.ApiResponse;
import com.ecommerce.product_service.dto.response.BrandResponse;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.entity.Brand;
import com.ecommerce.product_service.enums.MessageSuccess;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/brands")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {
    BrandService brandService;

    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<PageResponse<BrandResponse>>> getBrandsPaginate (
            @Filter Specification<Brand> specification,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "filter", defaultValue = "null") String filter
    ) {
            PageResponse<BrandResponse> responses = this.brandService.getBrands(specification, pageable, filter);
            return ResponseUtils.ok(MessageSuccess.BRAND_LIST_RETRIEVED.getMessage(), responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandById (@PathVariable("id") String id) {
        BrandResponse response = this.brandService.getBrandById(id);
        return ResponseUtils.ok(MessageSuccess.BRAND_RETRIEVED_SUCCESSFULLY.getMessage(), response);
    }

    @GetMapping("/select/infinity")
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getBrandSelectInfinity() {
        List<BrandResponse> responses = this.brandService.selectBrandInfinity();
        return ResponseUtils.ok(MessageSuccess.BRAND_SELECT_INFINITY_SUCCESSFULLY.getMessage(), responses);
    }

    @GetMapping("/slugs")
    public ResponseEntity<ApiResponse<Map<UUID, String>>> getSlugs (@RequestParam("name") String slug) {
        Map<UUID, String> uuidStringMap = this.brandService.getSlugs(slug);
        return ResponseUtils.ok(MessageSuccess.SLUG_LIST_RETRIEVED_SUCCESSFULLY.getMessage(), uuidStringMap);
    }

    @GetMapping("/code")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandByCode (@RequestParam("value") String code) {
        BrandResponse response = this.brandService.getByCodes(code);
        return ResponseUtils.ok(MessageSuccess.BRAND_RETRIEVED_BY_CODE_SUCCESSFULLY.getMessage(), response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BrandResponse>> createBrand (@RequestBody @Valid BrandRequest brandRequest) {
        BrandResponse brandResponse = this.brandService.createBrand(brandRequest);
        return ResponseUtils.ok(MessageSuccess.BRAND_CREATED_SUCCESSFULLY.getMessage(), brandResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> updateBrand (@PathVariable("id") String id,
                                                                   @RequestBody @Valid BrandUpdateRequest brandUpdateRequest) {
        BrandResponse brandResponse = this.brandService.updateBrand(id, brandUpdateRequest);
        return  ResponseUtils.ok(MessageSuccess.BRAND_UPDATED_SUCCESSFULLY.getMessage(), brandResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand (@PathVariable("id") String id) {
        this.brandService.deleteBrand(id);
        return ResponseUtils.ok(MessageSuccess.BRAND_DELETED_SUCCESSFULLY.getMessage(), null);
    }
}
