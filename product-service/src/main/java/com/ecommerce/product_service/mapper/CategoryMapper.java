package com.ecommerce.product_service.mapper;

import com.ecommerce.product_service.dto.request.CategoryRequest;
import com.ecommerce.product_service.dto.request.CategoryUpdateRequest;
import com.ecommerce.product_service.dto.response.CategoryDetailResponse;
import com.ecommerce.product_service.dto.response.CategoryResponse;
import com.ecommerce.product_service.dto.response.CategoryTreeResponse;
import com.ecommerce.product_service.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity (CategoryRequest request);
    CategoryResponse toResponse (Category category);
    CategoryDetailResponse toDetailResponse(Category category);
    void toUpdate (@MappingTarget Category category, CategoryUpdateRequest updateRequest);
    @Mapping(source = "children", target = "children")
    CategoryTreeResponse toViewTree (Category category);

    List<CategoryTreeResponse> toViewListTree (List<Category> categories);
}
