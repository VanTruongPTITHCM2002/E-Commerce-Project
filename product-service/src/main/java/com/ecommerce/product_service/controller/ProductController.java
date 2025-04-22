package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.dto.response.ApiResponse;
import com.ecommerce.product_service.service.ProductService;
import com.ecommerce.product_service.utils.ResponseUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ProductController {

    ProductService productService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ProductResponse>>>getProducts(){
        List<ProductResponse> productResponses = productService.getProducts();
        return ResponseEntity.ok().body(ResponseUtil.success(200,"Get successful data products",productResponses));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable("productId") String productId){
        Product product = this.productService.getProductById(productId);
        if(product == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtil.error(404,"Not found product",null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseUtil.success(200,"Found product successfully",product));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Product>> insertProduct(@RequestBody ProductRequest productRequest){
        Product product = productService.insertProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseUtil.success(201,"Insert product successfully",product));
    }

    @PutMapping("/{productId}")
    public  ResponseEntity<ApiResponse<ProductResponse>> updateProduct (@PathVariable String productId, @RequestBody ProductRequest productRequest){
        ProductResponse productResponse = this.productService.updateProduct(productId,productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseUtil.success(200,"Update product successfully",productResponse));
    }

    @DeleteMapping("/{productId}")
    public  ResponseEntity<ApiResponse<Map<String,Boolean>>> deleteProduct (@PathVariable String productId){
        boolean isDeletedProduct = this.productService.deleteProduct(productId);
        Map<String,Boolean> map = new HashMap<>();
        map.put("isDelete",isDeletedProduct);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseUtil.success(200,"Delete product successfully",map));
    }
}
