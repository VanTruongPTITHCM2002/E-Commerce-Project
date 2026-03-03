package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.request.ProductVariantRequest;
import com.ecommerce.product_service.entity.Brand;
import com.ecommerce.product_service.entity.Category;
import com.ecommerce.product_service.entity.ProductVariant;
import com.ecommerce.product_service.enums.EntityStatus;
import com.ecommerce.product_service.enums.MessageError;
import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.dto.response.ProductAdminResponse;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.exception.ConflictException;
import com.ecommerce.product_service.exception.NotFoundException;
import com.ecommerce.product_service.exception.ProductAlreadyExistsException;
import com.ecommerce.product_service.exception.ProductNotFoundException;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.repository.BrandRepository;
import com.ecommerce.product_service.repository.CategoryRepository;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.repository.ProductVariantRepository;
import com.ecommerce.product_service.service.ProductService;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Slf4j
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    BrandRepository brandRepository;
    ProductVariantRepository productVariantRepository;
    ProductMapper productMapper;


    @Override
    @Cacheable("products")
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

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductAdminResponse> getProducts(Specification<Product> specification, Pageable pageable, String filter) {
        log.info("Start get products by paginate and filter");
        try {
            Page<Product> productPage = this.productRepository.findAll(specification, pageable);

            Page<ProductAdminResponse> adminResponseList = productPage.map(
                    this.productMapper::toProductAdminResponse);

            return new PageResponse<>(adminResponseList, "Products", filter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            log.info("End get products by paginate and filter");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getPublishProduct(Pageable pageable, BigInteger minPrice, BigInteger maxPrice, String keyword) {
        Specification<Product> specification = Specification.where((root, query, cb) -> cb.isFalse(root.get("isDeleted")));

        specification = specification.and(Optional.ofNullable(minPrice)
                        .map(price -> (Specification<Product>)(root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), price))
                        .orElse(null)
                ).and(Optional.ofNullable(maxPrice)
                        .map(price -> (Specification<Product>)(root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), price))
                        .orElse(null))
                .and(Optional.ofNullable(keyword).map(name ->(Specification<Product>) (root, query, cb) -> cb.like(
                        cb.lower(root.get("name")), "%" + name.toLowerCase() + "%")).orElse(null)
                );
        Page<Product> products = this.productRepository.findAll(specification, pageable);
        return products.map(productMapper::toProductResponse);
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    @Transactional
    public ProductAdminResponse insertProduct(ProductRequest productRequest) {
        try{
            log.info("Start create product");
            this.validateExistsByName(productRequest.getName());
            Category category = this.validateCategory(productRequest.getCategoryId());
            Brand brand = this.validateBrand(productRequest.getBrandId());
            Product product = productMapper.toProduct(productRequest);
            product.setCategory(category);
            product.setBrand(brand);
            List<ProductVariant> variantList = new ArrayList<>();
//            for (ProductVariantRequest variantRequest: productRequest.getVariants()) {
//                ProductVariant productVariant = new ProductVariant();
//                productVariant.setProduct(product);
//            }
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

    private Category validateCategory (String categoryId) {
        return this.categoryRepository.findById(
                UUID.fromString(categoryId)
        ).orElseThrow(
                () -> new NotFoundException("Category not found")
        );
    }

    private Brand validateBrand (String brandId) {
        return this.brandRepository.findById(
                UUID.fromString(brandId)
        ).orElseThrow(
                () -> new NotFoundException("Brand not found")
        );
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
    public ProductAdminResponse updateProduct(String productId, ProductRequest productRequest) {
        Product product = this.productRepository.findById(UUID.fromString(productId))
                .orElseThrow(() -> new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getMessage()));
        productMapper.updateProduct(product,productRequest);
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
        Product product = this.productRepository.findById(UUID.fromString(productId))
                . orElseThrow(() -> new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getMessage()));
        product.setDeleted(true);
        product.setDeletedAt(ZonedDateTime.now());
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

    private Product validateProductId (UUID id) {
        return this.productRepository.findById(id)
                .filter(p -> EntityStatus.ACTIVE.equals(p.getEntityStatus()))
                .orElseThrow(
                        () -> new NotFoundException("Product not found")
                );
    }
}
