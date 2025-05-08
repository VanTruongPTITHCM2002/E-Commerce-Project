package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Slf4j
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;

    @Override
    public Page<ProductResponse> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toProductResponse);
    }

    @Override
    public Product insertProduct(ProductRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        this.productRepository.save(product);
        return product;
    }

    @Override
    public Product getProductById(String productId) {
        return this.productRepository.findById(UUID.fromString(productId)).
                orElseThrow(
                        () -> new RuntimeException("Product not found")
                );
    }

    @Override
    public ProductResponse updateProduct(String productId, ProductRequest productRequest) {
        Product product = this.productRepository.findById(UUID.fromString(productId)).orElse(null);
        if(product == null){
            return null;
        }
        productMapper.updateProduct(product,productRequest);
        this.productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public boolean deleteProduct(String productId) {
        Product product = this.productRepository.findById(UUID.fromString(productId))
                .orElseThrow(
                        () -> new RuntimeException("Product not found")
                );
        this.productRepository.delete(product);
        return true;
    }
}
