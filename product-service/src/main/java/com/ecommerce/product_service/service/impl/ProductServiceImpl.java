package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.common.MessageError;
import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.dto.response.ProductAdminResponse;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.exception.ProductAlreadyExistsException;
import com.ecommerce.product_service.exception.ProductNotFoundException;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.repository.ProductRepository;
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
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Slf4j
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;

    @Override
    @Cacheable("products")
    @Transactional(readOnly = true)
    public Page<ProductAdminResponse> getProducts(Pageable pageable, BigInteger minPrice, BigInteger maxPrice,
                                                  String keyWord, Boolean isDeleted, LocalDate startDate, LocalDate endDate) {
        Specification<Product> specification = Specification.where(null);
        specification = specification.and(Optional.ofNullable(minPrice)
                        .map(price -> (Specification<Product>)(root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), price))
                        .orElse(null))
                .and(Optional.ofNullable(maxPrice)
                        .map(price -> (Specification<Product>)(root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), price))
                        .orElse(null))
                .and(Optional.ofNullable(keyWord)
                        .map(name ->(Specification<Product>) (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"))
                        .orElse(null))
                .and(Optional.ofNullable(isDeleted)
                        .map(delete -> (Specification<Product>)(root, query, cb)-> cb.equal(root.get("isDeleted"),delete))
                        .orElse(null))
                .and(Optional.ofNullable(startDate).map(start -> (Specification<Product>)(root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createAt"), start))
                        .orElse(null))
                .and(Optional.ofNullable(endDate).map(end -> (Specification<Product>)(root, query, cb) -> cb.lessThanOrEqualTo(root.get("createAt"), end))
                        .orElse(null));

        Page<Product> products= this.productRepository.findAll(specification, pageable);
        return products.map(productMapper::toProductAdminResponse);
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
    @Transactional(readOnly = true)
    public ProductAdminResponse insertProduct(ProductRequest productRequest) {
        try{
            log.info("Start create product");
            boolean isExistsName = this.productRepository.existsByName(productRequest.getName());
            if(isExistsName) throw new ProductAlreadyExistsException(MessageError.INVALID_PRODUCT_DATA.getMessage());
            Product product = productMapper.toProduct(productRequest);
            this.productRepository.save(product);
            return productMapper.toProductAdminResponse(product);
        }catch (Exception e) {
            throw  e;
        } finally {
            log.info("End create product");
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
        if(isAdd) product.setQuantity(product.getQuantity().add(BigDecimal.valueOf(quantity)));
        else {
            product.setQuantity(product.getQuantity().compareTo(BigDecimal.valueOf(quantity)) < 0 ? product.getQuantity().subtract(BigDecimal.valueOf(quantity)) : product.getQuantity());
        }
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
}
