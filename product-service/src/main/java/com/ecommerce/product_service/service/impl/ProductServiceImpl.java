package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.request.ProductUpdateRequest;
import com.ecommerce.product_service.dto.request.ProductUpdateStatusRequest;
import com.ecommerce.product_service.entity.Brand;
import com.ecommerce.product_service.entity.Category;
import com.ecommerce.product_service.enums.EntityStatus;
import com.ecommerce.product_service.enums.MessageError;
import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.dto.response.ProductAdminResponse;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.exception.NotFoundException;
import com.ecommerce.product_service.exception.ProductAlreadyExistsException;
import com.ecommerce.product_service.exception.ProductNotFoundException;
import com.ecommerce.product_service.exception.UnprocessableEntityException;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.service.BrandService;
import com.ecommerce.product_service.service.CategoryService;
import com.ecommerce.product_service.service.ProductService;
import com.google.common.base.Preconditions;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Slf4j
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    CategoryService categoryService;
    BrandService brandService;
    ProductMapper productMapper;

    @Override
    @Cacheable("products")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallBackGetProduct")
    @RateLimiter(name = "productService", fallbackMethod = "fallBackGetProductAdmin")
    @Transactional(readOnly = true)
    public PageResponse<ProductAdminResponse> getAdminProducts(Specification<Product> specification, Pageable pageable, String filter) {
        try {
            log.info("Start get products for admin");
            specification = specification.and((root, query, cb) -> cb.isFalse(root.get("isDeleted")));
            Page<Product> products = this.productRepository.findAll(specification, pageable);
            Page<ProductAdminResponse> productAdminResponses = products.map(productMapper::toProductAdminResponse);
            return new PageResponse<>(productAdminResponses, "Products for admin", filter);
        } finally {
            log.info("End get products for admin");
        }
    }

    public String fallBackGetProduct() {
        return "Products for admin failed";
    }

    public String fallBackGetProductAdmin() {
        return "Too many request. Please try again later";
    }

    @Override
    @Transactional
    public void createProductBulk(List<ProductRequest> productRequests) {
        List<Product> products = new ArrayList<>();
        for (ProductRequest productRequest: productRequests) {
            this.validateExistsByName(productRequest.getName());
            Category category = this.categoryService.validateCategory(productRequest.getCategoryId());
            Brand brand = this.brandService.validateBrand(productRequest.getBrandId());
            Product product = productMapper.toProduct(productRequest);
            product.setCategory(category);
            product.setBrand(brand);
            products.add(product);
        }
        this.productRepository.saveAll(products);
    }

    @Override
    @Transactional
    public void deleteProductBulk(List<String> productIds) {
        List<UUID> uuids = productIds.stream().map(UUID::fromString).toList();
        List<Product> products = new ArrayList<>();
        for (UUID uuid : uuids) {
            Product product = this.validateProductId(uuid);
            product.setUpdatedAt(ZonedDateTime.now());
            product.setUpdatedBy("system");
            product.setDeleted(true);
            product.setDeletedAt(ZonedDateTime.now());
            product.setEntityStatus(EntityStatus.DELETED);
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            product.setDeletedBy(username);
            products.add(product);
        }
        this.productRepository.saveAll(products);
    }

    @Override
    @Transactional
    public void updateProductBulk(Map<String, ProductUpdateRequest> productUpdateRequestMap) {
        List<Product> products = new ArrayList<>();
        for (Map.Entry<String, ProductUpdateRequest> entry: productUpdateRequestMap.entrySet()) {
            Product product = this.validateProductId(UUID.fromString(entry.getKey()));
            productMapper.updateProduct(product, entry.getValue());
            product.setUpdatedAt(ZonedDateTime.now());
            product.setUpdatedBy("system");
            products.add(product);
        }
        this.productRepository.saveAll(products);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getProducts(Specification<Product> specification, Pageable pageable, String filter) {
        log.info("Starting get products by paginating and filtering...");
        try {
            specification = specification.and(
                    (root, query, cb)
                    -> cb.equal(root.get("entityStatus"),EntityStatus.ACTIVE));
            Page<Product> productPage = this.productRepository.findAll(specification, pageable);
            Page<ProductResponse> productResponses = productPage.map(
                    this.productMapper::toProductResponse);
            return new PageResponse<>(productResponses, "Products resource", filter);
        } finally {
            log.info("End get products by paginating and filtering...");
        }
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    @Transactional
    public ProductAdminResponse insertProduct(ProductRequest productRequest) {
        try{
            log.info("Start create product");
            this.validateExistsByName(productRequest.getName());
            Category category = this.categoryService.validateCategory(productRequest.getCategoryId());
            Brand brand = this.brandService.validateBrand(productRequest.getBrandId());
            Product product = productMapper.toProduct(productRequest);
            product.setCategory(category);
            product.setBrand(brand);
            this.productRepository.save(product);
            log.info("Created product successfully with id: {}", product.getId());
            return productMapper.toProductAdminResponse(product);
        } finally {
            log.info("End create product");
        }
    }

    private void validateExistsByName (String name) {
        boolean isExistsName = this.productRepository.existsByName(name);
        if(isExistsName) {
            throw new ProductAlreadyExistsException(MessageError.INVALID_PRODUCT_DATA.getMessage());
        }
    }

    @Override
    @Cacheable(value = "productById", key = "#productId")
    @Transactional(readOnly = true)
    public ProductAdminResponse getProductById(String productId) {
        return productMapper.toProductAdminResponse(this.productRepository.findById(UUID.fromString(productId)).
                orElseThrow(() -> new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getMessage())));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductDetail(String productId) {
        return productMapper.toProductResponse(this.productRepository.findById(UUID.fromString(productId)).filter(
                product -> !product.isDeleted()
        ). orElseThrow(() -> new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getMessage())));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "productById", key = "#productId"),
            @CacheEvict(value = "products", allEntries = true)
    })
    @Transactional
    public ProductAdminResponse updateProduct(String productId, ProductUpdateRequest updateRequest) {
        Product product = this.validateProductId(UUID.fromString(productId));
        productMapper.updateProduct(product, updateRequest);
        this.productRepository.save(product);
        return productMapper.toProductAdminResponse(product);
    }

    @Override
    @Transactional
    public boolean updateProductFromCart(String productId, int quantity, boolean isAdd) {
        Product product = this.productRepository.findById(UUID.fromString(productId))
                .orElseThrow(() -> new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getMessage()));
//        if(isAdd) product.setQuantity(product.getQuantity().add(BigDecimal.valueOf(quantity)));
//        else {
//            product.setQuantity(product.getQuantity().compareTo(BigDecimal.valueOf(quantity)) < 0 ? product.getQuantity().subtract(BigDecimal.valueOf(quantity)) : product.getQuantity());
//        }
        this.productRepository.save(product);
        return true;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "productById", key = "#productId"),
            @CacheEvict(value = "products", allEntries = true)
    })
    @Transactional
    public boolean deleteProduct(String productId) {
        Product product = this.validateProductId(UUID.fromString(productId));
        product.setUpdatedAt(ZonedDateTime.now());
        product.setUpdatedBy("system");
        product.setDeleted(true);
        product.setDeletedAt(ZonedDateTime.now());
        product.setEntityStatus(EntityStatus.DELETED);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        product.setDeletedBy(username);
        this.productRepository.save(product);
        return true;
    }

    @Override
    public Product checkExistsByIdAndReturn(String productId) {
        UUID id = UUID.fromString(productId);
        return this.validateProductId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategoryId(UUID categoryId) {
        List<Product> products = this.productRepository.getProductsWithCategory(categoryId);

        if (products.isEmpty()) {
            return Collections.emptyList();
        }

        return products.stream().map(
                product -> ProductResponse.builder()
                        .id(product.getId().toString())
                        .name(product.getName())
                        .build()
        ).toList();
    }

    @Override
    @Transactional
    public void changeStatus(ProductUpdateStatusRequest productUpdateStatusRequest) {
            UUID id = UUID.fromString(productUpdateStatusRequest.getId());
            Product product = this.validateProductId(id);
            EntityStatus entityStatus = EntityStatus.valueOf(productUpdateStatusRequest.getStatus());
            if (!product.getEntityStatus().canTransitionTo(entityStatus)) {
                throw new UnprocessableEntityException(MessageError.STATUS_TRANSITION_ERROR.getMessage());
            }

            product.setEntityStatus(entityStatus);
            product.setUpdatedAt(ZonedDateTime.now());
            product.setUpdatedBy("system");

            this.productRepository.save(product);
    }

    private Product validateProductId (UUID id) {
        return this.productRepository.findById(id)
                .filter(p -> EntityStatus.ACTIVE.equals(p.getEntityStatus()))
                .orElseThrow(
                        () -> new NotFoundException("Product not found")
                );
    }
}
