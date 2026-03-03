package com.ecommerce.product_service.service.impl;


import com.ecommerce.product_service.dto.request.CategoryRequest;
import com.ecommerce.product_service.dto.request.CategoryUpdateRequest;
import com.ecommerce.product_service.dto.response.CategoryDetailResponse;
import com.ecommerce.product_service.dto.response.CategoryResponse;
import com.ecommerce.product_service.dto.response.CategoryTreeResponse;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.entity.Category;
import com.ecommerce.product_service.enums.EntityStatus;
import com.ecommerce.product_service.exception.ConflictException;
import com.ecommerce.product_service.exception.NotFoundException;
import com.ecommerce.product_service.mapper.CategoryMapper;
import com.ecommerce.product_service.repository.CategoryRepository;
import com.ecommerce.product_service.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        this.validateCategorySlug(categoryRequest.getSlug());
        Category category = this.categoryMapper.toEntity(categoryRequest);
        this.checkParentAndReturn(category, categoryRequest.getParentId());
        this.checkChildrenAndReturn(category, categoryRequest.getChildren());
        this.categoryRepository.save(category);
        return this.categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDetailResponse getCategoryById(String id) {
        Category category = this.categoryRepository.findById(UUID.fromString(id))
                .filter(c -> EntityStatus.ACTIVE.equals(c.getEntityStatus()))
                .orElseThrow(
                        () -> new NotFoundException("Category not found")
                );
        CategoryDetailResponse categoryDetailResponse =  this.categoryMapper.toDetailResponse(category);
        if (!Objects.isNull(category.getParent())) {
            categoryDetailResponse.setParentId(category.getParent().getId());
            categoryDetailResponse.setParentName(category.getParent().getName());
        }
        return categoryDetailResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CategoryResponse> getCategories(Specification<Category> specification, Pageable pageable, String filter) {
       specification = specification.and(((root, query, criteriaBuilder) ->
                    root.get("entityStatus").in(
                            List.of(EntityStatus.ACTIVE)
                    )
               ));
        Page<Category> categoryPage = this.categoryRepository.findAll(specification, pageable);
        Page<CategoryResponse> responses = categoryPage.map(this.categoryMapper::toResponse);
        return new PageResponse<>(responses, "Categories", filter);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(String id, CategoryUpdateRequest categoryUpdateRequest) {
        Category category = this.categoryRepository.findById(UUID.fromString(id))
                .filter(c -> EntityStatus.ACTIVE.equals(c.getEntityStatus()))
                .orElseThrow(
                        () -> new NotFoundException("Category not found")
                );
        if (!Objects.isNull(categoryUpdateRequest.getParentId())) {
            if (categoryUpdateRequest.getParentId().equals(id)) {
                throw new ConflictException("Category cannot be parent of itself");
            }

            Category parent =  this.categoryRepository.findById(UUID.fromString(categoryUpdateRequest.getParentId()))
                    .filter(c -> EntityStatus.ACTIVE.equals(c.getEntityStatus()))
                    .orElseThrow(
                            () -> new NotFoundException("Category not found")
                    );

            if (isDescendant(category, parent)) {
                throw new ConflictException("Cannot move category to its own subtree");
            }
            category.setParent(parent);
        }

        this.categoryMapper.toUpdate(category, categoryUpdateRequest);
        return this.categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryTreeResponse viewTreeCategory(String id) {
        Category category = this.categoryRepository.findById(UUID.fromString(id))
                .filter(c -> EntityStatus.ACTIVE.equals(c.getEntityStatus()))
                .orElseThrow(
                        () -> new NotFoundException("Category not found")
                );
        return this.categoryMapper.toViewTree(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryTreeResponse> viewTreeCategories() {
        List<Category> categories = this.categoryRepository.findAll()
                .stream()
                .filter(
                        c -> EntityStatus.ACTIVE.equals(c.getEntityStatus())
                ).toList();

        return this.categoryMapper.toViewListTree(categories);
    }

    @Override
    @Transactional
    public void deleteCategory(String id) {
        Category category = this.categoryRepository.findById(UUID.fromString(id))
                .filter(c -> EntityStatus.ACTIVE.equals(c.getEntityStatus()))
                .orElseThrow(
                        () -> new NotFoundException("Category not found")
                );
        if (!category.getProducts().isEmpty()) {
            throw new ConflictException("Cannot delete category has products");
        }

        if (!category.getChildren().isEmpty()) {
            throw  new ConflictException("Cannot delete category with children");
        }

        category.setEntityStatus(EntityStatus.INACTIVE);
        category.setDeletedAt(ZonedDateTime.now());
        category.setDeletedBy(UUID.randomUUID().toString());
        this.categoryRepository.save(category);
    }

    private void validateCategorySlug (String slug) {
        boolean isExistsSlug = this.categoryRepository.existsBySlug(slug);

        if (isExistsSlug) {
            throw new ConflictException("Slug was existed");
        }
    }

    private boolean isDescendant (Category current, Category targetParent) {
        if (Objects.isNull(current.getChildren())) {
            return false;
        }

        for (Category child: current.getChildren()) {
            if (child.getId().equals(targetParent.getId())) {
                return true;
            }

            if (isDescendant(child, targetParent)) {
                return true;
            }
        }
        return  false;
    }

    private void checkParentAndReturn (Category category, String parentId) {
        if (StringUtils.hasText(parentId)) {
            Category parent = this.categoryRepository.findById(UUID.fromString(parentId))
                    .orElseThrow(
                            () -> new ConflictException("Category not found")
                    );
            category.setParent(parent);
        }
    }

    private void checkChildrenAndReturn (Category category, List<CategoryRequest> categoryRequests) {
        if (!Objects.isNull(categoryRequests)) {
            if (!categoryRequests.isEmpty()) {
                List<Category> categories = new ArrayList<>();
                for (CategoryRequest request: categoryRequests) {
                    this.validateCategorySlug(request.getSlug());
                    Category child = this.categoryMapper.toEntity(request);
                    categories.add(child);
                }
                category.setChildren(categories);
            }
        }
    }
}
