package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.request.BrandRequest;
import com.ecommerce.product_service.dto.request.BrandUpdateRequest;
import com.ecommerce.product_service.dto.response.BrandResponse;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.entity.Brand;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BrandService {
    BrandResponse createBrand (BrandRequest brandRequest);
    PageResponse<BrandResponse> getBrands (Specification<Brand> specification,
                                           Pageable pageable, String filter);
    BrandResponse getBrandById (String id);
    Map<UUID, String> getSlugs (String slug);
    BrandResponse getByCodes (String code);
    BrandResponse updateBrand (String id, BrandUpdateRequest brandUpdateRequest);
    void deleteBrand (String id);
    List<BrandResponse> selectBrandInfinity ();
    Brand validateBrand (String brandId);
    void createBulkBrand(List<BrandRequest> requestList);
    void updateBulkBrand(Map<String, BrandUpdateRequest> map);
    void deleteBulkBrand(List<String> brandIds);
    void changeStatus (String id, String status);
}
