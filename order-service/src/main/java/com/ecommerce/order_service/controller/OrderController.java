package com.ecommerce.order_service.controller;


import com.ecommerce.order_service.Enum.OrderStatus;
import com.ecommerce.order_service.common.MessageSuccess;
import com.ecommerce.order_service.dto.request.OrderItemRequest;
import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.ApiResponse;
import com.ecommerce.order_service.dto.response.OrderItemResponse;
import com.ecommerce.order_service.dto.response.OrderResponse;
import com.ecommerce.order_service.service.IOrderService;
import com.ecommerce.order_service.utils.ResponseUtils;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    IOrderService iOrderService;

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<OrderResponse>>> getOrders(
            @RequestParam (name = "size", defaultValue = "5") int size,
            @RequestParam (name = "page", defaultValue = "0") int page,
            @RequestParam (name = "sorting", defaultValue = "createAt") String sorting,
            @RequestParam(name = "userId", required = false) String userId,
            @RequestParam(name = "status", required = false) String status
    ){
        Pageable pageable = PageRequest.of(size,page, Sort.by(sorting).descending());
        Page<OrderResponse> orderResponseList = this.iOrderService.getOrders(pageable, userId, status);
        return ResponseUtils.ok(MessageSuccess.ORDER_LIST_RETRIEVED_SUCCESSFULLY.getMessage(), orderResponseList);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse<Page<OrderResponse>>> getOrdersByUserId (
            @PathVariable String userId,
            @RequestParam (name = "size", defaultValue = "5") int size,
            @RequestParam (name = "page", defaultValue = "0") int page,
            @RequestParam (name = "sorting", defaultValue = "createAt") String sorting,
            @RequestParam (name = "status", required = false) String status
            ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorting).descending());
        Page<OrderResponse> responses = this.iOrderService.getOrdersByUserId(pageable,userId, status);
        return ResponseUtils.ok(MessageSuccess.ORDER_LIST_RETRIEVED_SUCCESSFULLY.getMessage(), responses);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderByOrderId(@PathVariable int orderId){
        OrderResponse response = this.iOrderService.getOrderById(orderId);
        return  ResponseUtils.ok(MessageSuccess.ORDER_RETRIEVED_SUCCESSFULLY.getMessage(), response);
    }

    @PostMapping("{userId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> addOrder (
            @PathVariable String userId){
        OrderResponse orderResponse = this.iOrderService.addOrder(userId);
        return ResponseUtils.create(MessageSuccess.ORDER_CREATED_SUCCESSFULLY.getMessage(), orderResponse);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrder (
            @RequestBody @Valid OrderRequest orderRequest,
            @PathVariable int orderId){
        OrderResponse orderResponse = this.iOrderService.updateOrder(orderId, orderRequest);
       return ResponseUtils.ok(MessageSuccess.ORDER_UPDATED_SUCCESSFULLY.getMessage(), orderResponse);
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<ApiResponse<String>> updateOrderStatus (@PathVariable int orderId
    , @RequestParam String orderStatus){
        this.iOrderService.updateOrderStatus(orderId, OrderStatus.valueOf(orderStatus));
        return ResponseUtils.ok(MessageSuccess.ORDER_STATUS_UPDATED_SUCCESSFULLY.getMessage());
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteOrder (@PathVariable int orderId){
        boolean isDeleted = this.iOrderService.deleteOrder(orderId);
        return ResponseEntity.ok().body(
                ApiResponse.<Boolean>builder()
                        .status(HttpStatus.OK.value())
                        .message(isDeleted ? "Deleted successfully" : "Deleted failure")
                        .build()
        );
    }
}
