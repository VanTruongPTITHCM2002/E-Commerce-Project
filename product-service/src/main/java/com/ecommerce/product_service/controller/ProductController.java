package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.request.ProductUpdateRequest;
import com.ecommerce.product_service.dto.request.ProductUpdateStatusRequest;
import com.ecommerce.product_service.enums.MessageSuccess;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Slf4j
@Tag(
        name = "Product Management",
        description = "APIs for managing products in the system"
)
public class ProductController {

    ProductService productService;

    @GetMapping("/admin")
//    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<PageResponse<ProductAdminResponse>>>getProducts(
            @Filter Specification<Product> specification,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "filter", defaultValue = "null") String filter
    ){
        PageResponse<ProductAdminResponse> responses = this.productService.getAdminProducts(specification, pageable, filter);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_LIST_RETRIEVED.getMessage(),responses);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getProductsPaginate (
            @Filter Specification<Product> specification,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "filter", defaultValue = "null") String filter
            ) {
        PageResponse<ProductResponse> responses = this.productService.getProducts(specification, pageable, filter);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_LIST_RETRIEVED.getMessage(), responses);
    }

    @GetMapping("/admin/{productId}")
//    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<ProductAdminResponse>> getProductById(@PathVariable("productId") String productId){
        ProductAdminResponse productResponse = this.productService.getProductById(productId);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_RETRIEVED_SUCCESSFULLY.getMessage(), productResponse);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductDetail (@PathVariable String productId){
        ProductResponse response = this.productService.getProductDetail(productId);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_RETRIEVED_SUCCESSFULLY.getMessage(), response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByCategory(
            @PathVariable("categoryId") String categoryId
    ) {
        UUID id = UUID.fromString(categoryId);
        List<ProductResponse> productResponseList = this.productService.getProductsByCategoryId(id);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_LIST_RETRIEVED.getMessage(), productResponseList);
    }

    @PostMapping("/admin")
//    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    @Operation(summary = "Create a new product", description = "Add a new product to the system")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Product created successfully",
            content = @Content(schema = @Schema(implementation = ProductResponse.class))),
    })
    public ResponseEntity<ApiResponse<ProductAdminResponse>> insertProduct(@RequestBody @Valid ProductRequest productRequest){
        ProductAdminResponse response =  this.productService.insertProduct(productRequest);
        return ResponseUtils.create(MessageSuccess.PRODUCT_CREATED_SUCCESSFULLY.getMessage(), response);
    }

    @PostMapping("/admin/create-bulk")
//    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<ProductAdminResponse>> createProductBulk(@RequestBody @Valid List<ProductRequest> productRequest){
        this.productService.createProductBulk(productRequest);
        return ResponseUtils.create(MessageSuccess.PRODUCT_CREATED_BULK_SUCCESSFULLY.getMessage(), null);
    }

    @PutMapping("/admin/{productId}")
//    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public  ResponseEntity<ApiResponse<ProductAdminResponse>> updateProduct (@PathVariable("productId") String productId, @RequestBody @Valid ProductUpdateRequest productUpdateRequest){
        ProductAdminResponse response = this.productService.updateProduct(productId, productUpdateRequest);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_UPDATED_SUCCESSFULLY.getMessage(), response);
    }

    @PutMapping("/admin/update-bulk")
//    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<Void>> updateProductBulk(@RequestBody Map<String, ProductUpdateRequest> updateRequestMap){
        this.productService.updateProductBulk(updateRequestMap);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_UPDATED_SUCCESSFULLY.getMessage(), null);
    }

    @PutMapping("/{productId}")
//    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<Boolean>> updateProductFromCart(@PathVariable String productId,
                                                                      @RequestParam int quantity, @RequestParam boolean isAdd){
        Boolean isSuccess = this.productService.updateProductFromCart(productId, quantity, isAdd);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_UPDATED_SUCCESSFULLY.getMessage(), isSuccess);
    }

    @DeleteMapping("/admin/{productId}")
//    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public  ResponseEntity<ApiResponse<Map<String,Boolean>>> deleteProduct (@PathVariable String productId){
        boolean isDeletedProduct = this.productService.deleteProduct(productId);
        Map<String,Boolean> map = new HashMap<>();
        map.put("isDelete",isDeletedProduct);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_DELETED_SUCCESSFULLY.getMessage(), map);
    }

    @DeleteMapping("/admin/delete-bulk")
//    @PreAuthorize(RoleConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<Void>> deleteProductBulk(@RequestBody List<String>productIds){
        this.productService.deleteProductBulk(productIds);
        return ResponseUtils.ok(MessageSuccess.PRODUCT_DELETED_BULK_SUCCESSFULLY.getMessage(), null);
    }

    @PutMapping("/admin/change-status")
    public ResponseEntity<ApiResponse<Void>> changeStatusProduct (@RequestBody ProductUpdateStatusRequest productUpdateStatusRequest) {
        this.productService.changeStatus(productUpdateStatusRequest);
        return ResponseUtils.ok(MessageSuccess.STATUS_TRANSITION_SUCCESSFULLY.getMessage(), null);
    }
}
