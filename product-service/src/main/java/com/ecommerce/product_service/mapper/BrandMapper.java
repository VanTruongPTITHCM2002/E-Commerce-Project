package com.ecommerce.product_service.mapper;

import com.ecommerce.product_service.dto.request.BrandRequest;
import com.ecommerce.product_service.dto.response.BrandResponse;
import com.ecommerce.product_service.entity.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    Brand toEntity (BrandRequest brandRequest);

    BrandResponse toResponse (Brand brand);
}
