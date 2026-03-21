package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.request.CategoryRequest;
import com.ecommerce.product_service.dto.request.CategoryUpdateRequest;
import com.ecommerce.product_service.dto.response.CategoryDetailResponse;
import com.ecommerce.product_service.dto.response.CategoryResponse;
import com.ecommerce.product_service.dto.response.CategoryTreeResponse;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    CategoryResponse createCategory (CategoryRequest categoryRequest);
    CategoryDetailResponse getCategoryById (String id);
    PageResponse<CategoryResponse> getCategories(Specification<Category> specification, Pageable pageable, String filter);
    CategoryResponse updateCategory (String id, CategoryUpdateRequest categoryUpdateRequest);
    CategoryTreeResponse viewTreeCategory (String id);
    List<CategoryTreeResponse> viewTreeCategories ();
    void deleteCategory (String id);
    Category validateCategory (String categoryId);
    List<CategoryResponse> getRoot();
    void createBulkCategory (List<CategoryRequest> categoryRequests);
    void updateBulkCategory (Map<String, CategoryUpdateRequest> updateRequestMap);
    void deleteBulkCategory (List<String> categoryIds);
}
