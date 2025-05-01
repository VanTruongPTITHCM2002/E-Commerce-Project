package com.ecommerce.product_service.service;


import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {
    Page<ProductResponse> getProducts(Pageable pageable);
    Product insertProduct(ProductRequest productDto);
    Product getProductById(String productId);
    ProductResponse updateProduct(String productId, ProductRequest productRequest);
    boolean deleteProduct(String productId);
 }
