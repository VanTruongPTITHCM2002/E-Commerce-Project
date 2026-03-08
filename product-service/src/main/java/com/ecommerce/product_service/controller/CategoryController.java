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

    @GetMapping
//    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<PageResponse<CategoryResponse>>> getCategoriesPaginate (
            @Filter Specification<Category> specification,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "filter", required = false) String filter
            ) {
        PageResponse<CategoryResponse> responses = this.categoryService.getCategories(specification, pageable, filter);
        return  ResponseUtils.ok("Get Category paginate successfully", responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDetailResponse>> getDetail (@PathVariable("id") String id) {
        CategoryDetailResponse categoryDetailResponse = this.categoryService.getCategoryById(id);
        return ResponseUtils.ok("Get Category by " + id + " successfully", categoryDetailResponse);
    }

    @GetMapping("/{id}/tree")
    public ResponseEntity<ApiResponse<CategoryTreeResponse>> getViewTree (@PathVariable("id") String id) {
        CategoryTreeResponse categoryTreeResponse = this.categoryService.viewTreeCategory(id);
        return  ResponseUtils.ok("Get Category view tree successfully", categoryTreeResponse);
    }

    @GetMapping("/trees")
    public ResponseEntity<ApiResponse<List<CategoryTreeResponse>>> getTrees () {
        List<CategoryTreeResponse> responses = this.categoryService.viewTreeCategories();
        return ResponseUtils.ok("Get view trees categories successfully", responses);
    }

    @GetMapping("/root")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getRoot() {
        List<CategoryResponse> categoryResponses = this.categoryService.getRoot();
        return ResponseUtils.ok("Get root category successfully", categoryResponses);
    }

    @PutMapping("/{id}")
    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory (
            @PathVariable("id") String id,
            @RequestBody @Valid CategoryUpdateRequest updateRequest) {
        CategoryResponse response = this.categoryService.updateCategory(id, updateRequest);
        return ResponseUtils.ok("Updated category successfully", response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<Void>> deleteCategory (@PathVariable("id") String id) {
        this.categoryService.deleteCategory(id);
        return ResponseUtils.ok("Deleted category successfully", null);
    }
}
