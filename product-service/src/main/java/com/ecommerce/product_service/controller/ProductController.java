package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.ProductDto;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.response.ApiResponse;
import com.ecommerce.product_service.service.ProductService;
import com.ecommerce.product_service.utils.ResponseUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ProductController {

    ProductService productService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Product>>>getProducts(){
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok().body(ResponseUtil.success(200,"Get successful data products",products));
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
    public ResponseEntity<ApiResponse<Product>> insertProduct(@RequestBody ProductDto productDto){
        Product product = productService.insertProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseUtil.success(201,"Insert product successfully",product));
    }
}
