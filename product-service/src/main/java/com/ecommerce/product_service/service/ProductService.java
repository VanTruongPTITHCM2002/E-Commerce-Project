package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.ProductDto;
import com.ecommerce.product_service.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    Product insertProduct(ProductDto productDto);
}
