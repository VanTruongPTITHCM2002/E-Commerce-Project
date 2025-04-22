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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Slf4j
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;

    @Override
    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse).toList();
    }

    @Override
    public Product insertProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        productRepository.save(product);
        return product;
    }

    @Override
    public Product getProductById(String productId) {
        return this.productRepository.findById(UUID.fromString(productId)).orElse(null);
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
        Product product = this.productRepository.findById(UUID.fromString(productId)).orElse(null);
        if(product == null){
            return false;
        }

        this.productRepository.delete(product);
        return true;
    }
}
