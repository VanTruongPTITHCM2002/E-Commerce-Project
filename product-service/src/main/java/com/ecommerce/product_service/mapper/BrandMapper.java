package com.ecommerce.product_service.mapper;

import com.ecommerce.product_service.dto.request.BrandRequest;
import com.ecommerce.product_service.dto.request.BrandUpdateRequest;
import com.ecommerce.product_service.dto.response.BrandResponse;
import com.ecommerce.product_service.entity.Brand;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    Brand toEntity (BrandRequest brandRequest);

    @Mapping(source = "code", target = "code")
    @Mapping(source = "entityStatus", target = "status")
    BrandResponse toResponse (Brand brand);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdate (@MappingTarget Brand brand, BrandUpdateRequest brandUpdateRequest);
}
