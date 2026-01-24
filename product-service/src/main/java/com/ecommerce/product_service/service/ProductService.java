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


public interface ProductService {
    PageResponse<ProductAdminResponse> getAdminProducts(Specification<Product> specification, Pageable pageable, String filter);
    PageResponse<ProductAdminResponse> getProducts (Specification<Product> specification, Pageable pageable, String filter);
    Page<ProductResponse> getPublishProduct (Pageable pageable, BigInteger minPrice, BigInteger maxPrice, String keyword);
    ProductAdminResponse insertProduct(ProductRequest productDto);
    ProductAdminResponse getProductById(String productId);
    ProductResponse getProductDetail(String productId);
    ProductAdminResponse updateProduct(String productId, ProductRequest productRequest);
    boolean updateProductFromCart(String productId, int quantity, boolean isAdd);
    boolean deleteProduct(String productId);
 }
