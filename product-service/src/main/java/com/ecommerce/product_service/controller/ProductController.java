package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.common.MessageSuccess;
import com.ecommerce.product_service.constants.RoleConstants;
import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.dto.response.ProductAdminResponse;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.dto.response.ApiResponse;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.service.ProductService;
import com.ecommerce.product_service.utils.ResponseUtils;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Slf4j
public class ProductController {

    ProductService productService;

    @GetMapping("/admin/products")
    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<PageResponse<ProductAdminResponse>>>getProducts(
            @Filter Specification<Product> specification,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "filter", defaultValue = "null") String filter
    ){
        PageResponse<ProductAdminResponse> responses = this.productService.getProducts(specification, pageable, filter);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_LIST_RETRIEVED.getMessage(),responses);
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getPublishProducts (
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sorting", defaultValue = "name") String sorting,
            @RequestParam(name = "minPrice", required = false) BigInteger minPrice,
            @RequestParam(name = "maxPrice", required = false) BigInteger maxPrice,
            @RequestParam(name = "keyword", required = false) String keyword
    ){
        Pageable pageable = PageRequest.of(page, size,  Sort.by(sorting).descending());
        Page<ProductResponse> responses = this.productService.getPublishProduct(pageable, minPrice, maxPrice, keyword);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_LIST_RETRIEVED.getMessage(),responses);
    }

    @GetMapping("/products/paginate")
    public ResponseEntity<ApiResponse<PageResponse<ProductAdminResponse>>> getProductsPaginate (
            @Filter Specification<Product> specification,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "filter", defaultValue = "null") String filter
            ) {
        PageResponse<ProductAdminResponse> responses = this.productService.getProducts(specification, pageable, filter);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_LIST_RETRIEVED.getMessage(), responses);
    }

    @GetMapping("/admin/products/{productId}")
    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<ProductAdminResponse>> getProductById(@PathVariable("productId") String productId){
        ProductAdminResponse productResponse = this.productService.getProductById(productId);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_RETRIEVED_SUCCESSFULLY.getMessage(), productResponse);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductDetail (@PathVariable String productId){
        ProductResponse response = this.productService.getProductDetail(productId);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_RETRIEVED_SUCCESSFULLY.getMessage(), response);
    }

    @PostMapping("/admin/products")
    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<ProductAdminResponse>> insertProduct(@RequestBody @Valid ProductRequest productRequest){
        ProductAdminResponse response =  this.productService.insertProduct(productRequest);
        return ResponseUtils.create(MessageSuccess.PRODUCT_CREATED_SUCCESSFULLY.getMessage(), response);
    }

    @PutMapping("/admin/products/{productId}")
    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public  ResponseEntity<ApiResponse<ProductAdminResponse>> updateProduct (@PathVariable String productId, @RequestBody ProductRequest productRequest){
        ProductAdminResponse response = this.productService.updateProduct(productId,productRequest);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_UPDATED_SUCCESSFULLY.getMessage(), response);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<Boolean>> updateProductFromCart(@PathVariable String productId,
                                                                      @RequestParam int quantity, @RequestParam boolean isAdd){
        Boolean isSuccess = this.productService.updateProductFromCart(productId, quantity, isAdd);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_UPDATED_SUCCESSFULLY.getMessage(), isSuccess);
    }

    @DeleteMapping("/admin/products/{productId}")
    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public  ResponseEntity<ApiResponse<Map<String,Boolean>>> deleteProduct (@PathVariable String productId){
        boolean isDeletedProduct = this.productService.deleteProduct(productId);
        Map<String,Boolean> map = new HashMap<>();
        map.put("isDelete",isDeletedProduct);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_DELETED_SUCCESSFULLY.getMessage(), map);
    }
}
