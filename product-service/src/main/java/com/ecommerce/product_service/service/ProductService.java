package com.ecommerce.product_service.service;


import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.dto.response.ProductAdminResponse;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;


public interface ProductService {
    Page<ProductAdminResponse> getProducts(Pageable pageable, BigInteger minPrice, BigInteger maxPrice, String keyWord, Boolean isDeleted, LocalDate startDate, LocalDate endDate);
    PageResponse<ProductAdminResponse> getProducts (Specification<Product> specification, Pageable pageable, String filter);
    Page<ProductResponse> getPublishProduct (Pageable pageable, BigInteger minPrice, BigInteger maxPrice, String keyword);
    ProductAdminResponse insertProduct(ProductRequest productDto);
    ProductAdminResponse getProductById(String productId);
    ProductResponse getProductDetail(String productId);
    ProductAdminResponse updateProduct(String productId, ProductRequest productRequest);
    boolean updateProductFromCart(String productId, int quantity, boolean isAdd);
    boolean deleteProduct(String productId);
 }
