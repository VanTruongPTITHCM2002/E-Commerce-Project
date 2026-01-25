package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.request.BrandRequest;
import com.ecommerce.product_service.dto.response.BrandResponse;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.entity.Brand;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BrandService {
    BrandResponse createBrand (BrandRequest brandRequest);
    PageResponse<BrandResponse> getBrands (Specification<Brand> specification,
                                           Pageable pageable, String filter);
    BrandResponse getBrandById (String id);
    void deleteBrand (String id);
    List<BrandResponse> selectBrandInfinity ();
}
