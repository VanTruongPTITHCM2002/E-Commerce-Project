package com.ecommerce.product_service.service;


import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getProducts();
    Product insertProduct(ProductRequest productDto);
    Product getProductById(String productId);
    ProductResponse updateProduct(String productId, ProductRequest productRequest);
    boolean deleteProduct(String productId);
 }
