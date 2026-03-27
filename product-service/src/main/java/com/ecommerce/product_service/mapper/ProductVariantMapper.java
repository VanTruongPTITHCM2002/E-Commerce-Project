package com.ecommerce.product_service.mapper;

import com.ecommerce.product_service.dto.request.ProductVariantRequest;
import com.ecommerce.product_service.dto.request.ProductVariantUpdateRequest;
import com.ecommerce.product_service.dto.response.ProductVariantResponse;
import com.ecommerce.product_service.entity.ProductVariant;
import com.ecommerce.product_service.utils.JsonbUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring", imports = JsonbUtils.class,
 nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductVariantMapper {
    @Mapping(target = "attributes",expression = "java(JsonbUtils.toJsonString(productVariantRequest.getAttributes()))")
    ProductVariant toEntity (ProductVariantRequest productVariantRequest);
    @Mapping(target = "attributes",expression = "java(JsonbUtils.toJsonNode(productVariant.getAttributes()))")
    ProductVariantResponse toResponse (ProductVariant productVariant);
    @Mapping(target = "attributes",expression = "java(JsonbUtils.toJsonString(updateRequest.getAttributes()))")
    void toUpdate(ProductVariantUpdateRequest updateRequest, @MappingTarget ProductVariant productVariant);
}
