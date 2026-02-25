package com.ecommerce.product_service.mapper;

import com.ecommerce.product_service.dto.request.BrandRequest;
import com.ecommerce.product_service.dto.request.BrandUpdateRequest;
import com.ecommerce.product_service.dto.response.BrandResponse;
import com.ecommerce.product_service.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    Brand toEntity (BrandRequest brandRequest);

    @Mapping(source = "code", target = "code")
    BrandResponse toResponse (Brand brand);

    Brand toUpdate (@MappingTarget Brand brand, BrandUpdateRequest brandUpdateRequest);
}
