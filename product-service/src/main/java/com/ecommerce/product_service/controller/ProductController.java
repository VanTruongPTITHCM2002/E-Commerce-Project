package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.dto.response.ApiResponse;
import com.ecommerce.product_service.service.ProductService;
import com.ecommerce.product_service.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Slf4j
public class ProductController {

    ProductService productService;

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<ProductResponse>>>getProducts(
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sorting", defaultValue = "name") String sorting
    ){
        Pageable pageable = PageRequest.of(page, size,  Sort.by(sorting).descending()); // in frontend must be page + 1;
        Page<ProductResponse> productResponsePage = this.productService.getProducts(pageable);
        log.info("Get products have page, size, sorting successfully");
        return ResponseEntity.ok().body(ResponseUtil.success(200,"Get successful data products",productResponsePage));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable("productId") String productId){
        ProductResponse productResponse = this.productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseUtil.success(200,"Found product successfully",productResponse));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Product>> insertProduct(@RequestBody @Valid ProductRequest productRequest){
        Product product =  this.productService.insertProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseUtil.success(201,"Insert product successfully",product));
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ApiResponse<ProductResponse>> updateProduct (@PathVariable String productId, @RequestBody ProductRequest productRequest){
        ProductResponse productResponse = this.productService.updateProduct(productId,productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseUtil.success(200,"Update product successfully",productResponse));
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ApiResponse<Map<String,Boolean>>> deleteProduct (@PathVariable String productId){
        boolean isDeletedProduct = this.productService.deleteProduct(productId);
        Map<String,Boolean> map = new HashMap<>();
        map.put("isDelete",isDeletedProduct);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseUtil.success(200,"Delete product successfully"
                ,map));
    }
}
