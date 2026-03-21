package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.constants.RoleConstants;
import com.ecommerce.product_service.dto.request.CategoryRequest;
import com.ecommerce.product_service.dto.request.CategoryUpdateRequest;
import com.ecommerce.product_service.dto.response.*;
import com.ecommerce.product_service.entity.Category;
import com.ecommerce.product_service.enums.MessageSuccess;
import com.ecommerce.product_service.service.CategoryService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
//    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory (@RequestBody @Valid CategoryRequest categoryRequest) {
        CategoryResponse response = this.categoryService.createCategory(categoryRequest);
        return ResponseUtils.create(MessageSuccess.CATEGORY_CREATED_SUCCESSFULLY.getMessage(), response);
    }

    @PostMapping("/create-bulk")
    public ResponseEntity<ApiResponse<Void>> createBulkCategory (@RequestBody @Valid List<CategoryRequest> categoryRequests) {
        this.categoryService.createBulkCategory(categoryRequests);
        return ResponseUtils.create(MessageSuccess.CATEGORY_CREATED_BULK_SUCCESSFULLY.getMessage(), null);
    }

    @GetMapping
//    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<PageResponse<CategoryResponse>>> getCategoriesPaginate (
            @Filter Specification<Category> specification,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "filter", required = false) String filter
            ) {
        PageResponse<CategoryResponse> responses = this.categoryService.getCategories(specification, pageable, filter);
        return  ResponseUtils.ok(MessageSuccess.CATEGORY_LIST_RETRIEVED.getMessage(), responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDetailResponse>> getDetail (@PathVariable("id") String id) {
        CategoryDetailResponse categoryDetailResponse = this.categoryService.getCategoryById(id);
        return ResponseUtils.ok(MessageSuccess.CATEGORY_RETRIEVED_SUCCESSFULLY.getMessage(), categoryDetailResponse);
    }

    @GetMapping("/{id}/tree")
    public ResponseEntity<ApiResponse<CategoryTreeResponse>> getViewTree (@PathVariable("id") String id) {
        CategoryTreeResponse categoryTreeResponse = this.categoryService.viewTreeCategory(id);
        return  ResponseUtils.ok(MessageSuccess.CATEGORY_VIEW_TREE_SUCCESSFULLY.getMessage(), categoryTreeResponse);
    }

    @GetMapping("/trees")
    public ResponseEntity<ApiResponse<List<CategoryTreeResponse>>> getTrees () {
        List<CategoryTreeResponse> responses = this.categoryService.viewTreeCategories();
        return ResponseUtils.ok(MessageSuccess.CATEGORY_VIEW_TREES_SUCCESSFULLY.getMessage(), responses);
    }

    @GetMapping("/root")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getRoot() {
        List<CategoryResponse> categoryResponses = this.categoryService.getRoot();
        return ResponseUtils.ok(MessageSuccess.CATEGORY_VIEW_ROOT_SUCCESSFULLY.getMessage(), categoryResponses);
    }

    @PutMapping("/{id}")
    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory (
            @PathVariable("id") String id,
            @RequestBody @Valid CategoryUpdateRequest updateRequest) {
        CategoryResponse response = this.categoryService.updateCategory(id, updateRequest);
        return ResponseUtils.ok(MessageSuccess.CATEGORY_UPDATED_SUCCESSFULLY.getMessage(), response);
    }

    @PutMapping("/update-bulk")
    public ResponseEntity<ApiResponse<Void>> updateBulkCategory (@RequestBody Map<String,  CategoryUpdateRequest> updateRequestMap) {
        this.categoryService.updateBulkCategory(updateRequestMap);
        return ResponseUtils.ok(MessageSuccess.CATEGORY_UPDATED_BULK_SUCCESSFULLY.getMessage(), null);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<Void>> deleteCategory (@PathVariable("id") String id) {
        this.categoryService.deleteCategory(id);
        return ResponseUtils.ok(MessageSuccess.CATEGORY_DELETED_SUCCESSFULLY.getMessage(), null);
    }

    @DeleteMapping("/delete-bulk")
    public ResponseEntity<ApiResponse<Void>> deleteBulkCategory (@RequestBody List<String> ids) {
        this.categoryService.deleteBulkCategory(ids);
        return ResponseUtils.ok(MessageSuccess.CATEGORY_DELETED_BULK_SUCCESSFULLY.getMessage(), null);
    }
}
