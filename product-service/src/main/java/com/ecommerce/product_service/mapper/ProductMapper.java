package com.ecommerce.product_service.mapper;

import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.ProductAdminResponse;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toProductResponse(Product product);
    ProductAdminResponse toProductAdminResponse (Product product);
    Product toProduct (ProductRequest productRequest);
    void updateProduct(@MappingTarget Product product,ProductRequest productRequest);
}
