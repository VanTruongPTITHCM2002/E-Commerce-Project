package com.ecommerce.product_service.service;


import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.request.ProductUpdateRequest;
import com.ecommerce.product_service.dto.request.ProductUpdateStatusRequest;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.dto.response.ProductAdminResponse;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface ProductService {
    PageResponse<ProductAdminResponse> getAdminProducts(Specification<Product> specification, Pageable pageable, String filter);
    void createProductBulk(List<ProductRequest> productRequests);
    void deleteProductBulk(List<String> productIds);
    void updateProductBulk(Map<String, ProductUpdateRequest> productUpdateRequestMap);
    PageResponse<ProductResponse> getProducts (Specification<Product> specification, Pageable pageable, String filter);
    ProductAdminResponse insertProduct(ProductRequest productDto);
    ProductAdminResponse getProductById(String productId);
    ProductResponse getProductDetail(String productId);
    ProductAdminResponse updateProduct(String productId, ProductUpdateRequest updateRequest);
    boolean updateProductFromCart(String productId, int quantity, boolean isAdd);
    boolean deleteProduct(String productId);
    Product checkExistsByIdAndReturn(String productId);
    List<ProductResponse> getProductsByCategoryId (UUID categoryId);
    void changeStatus(ProductUpdateStatusRequest productUpdateStatusRequest);
 }
