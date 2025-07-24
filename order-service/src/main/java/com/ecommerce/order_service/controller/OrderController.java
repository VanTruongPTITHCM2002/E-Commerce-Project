package com.ecommerce.order_service.controller;


import com.ecommerce.order_service.Enum.OrderStatus;
import com.ecommerce.order_service.dto.request.OrderItemRequest;
import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.ApiResponse;
import com.ecommerce.order_service.dto.response.OrderItemResponse;
import com.ecommerce.order_service.dto.response.OrderResponse;
import com.ecommerce.order_service.service.IOrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrders(
            @RequestParam (name = "size", defaultValue = "5") int size,
            @RequestParam (name = "page", defaultValue = "0") int page,
            @RequestParam (name = "sorting", defaultValue = "createAt") String sorting
    ){
        Pageable pageable = PageRequest.of(size,page, Sort.by(sorting).descending());
        List<OrderResponse> orderResponseList = this.iOrderService.getOrders(pageable);
        return ResponseEntity.ok().body(
          ApiResponse.<List<OrderResponse>>builder()
                  .status(HttpStatus.OK.value())
                  .message("Get orders successfully")
                  .data(orderResponseList)
                  .build()
        );
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByUserId (
            @PathVariable String userId){
        List<OrderResponse> responses = this.iOrderService.getOrdersByUserId(userId);
        return ResponseEntity.ok().body(
                ApiResponse.<List<OrderResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get orders of " + userId + " successfully")
                        .data(responses)
                        .build()
        );
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderByOrderId(@PathVariable int orderId){
        OrderResponse response = this.iOrderService.getOrderById(orderId);
        return ResponseEntity.ok().body(ApiResponse.<OrderResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get order successfully")
                        .data(response)
                .build());
    }

    @PostMapping("{userId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> addOrder (
            @PathVariable String userId){
        OrderResponse orderResponse = this.iOrderService.addOrder(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<OrderResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Created order successfully")
                        .data(orderResponse)
                        .build()
        );
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrder (
            @RequestBody @Valid OrderRequest orderRequest,
            @PathVariable int orderId){
        OrderResponse orderResponse = this.iOrderService.updateOrder(orderId, orderRequest);
        return ResponseEntity.ok().body(
                ApiResponse.<OrderResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Updated order successfully")
                        .data(orderResponse)
                        .build()
        );
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<ApiResponse<String>> updateOrderStatus (@PathVariable int orderId
    , @RequestParam String orderStatus){
        this.iOrderService.updateOrderStatus(orderId, OrderStatus.valueOf(orderStatus));
        return ResponseEntity.ok().body(
                ApiResponse.<String>builder()
                        .status(HttpStatus.OK.value())
                        .message("Update status order successfully")
                        .build()
        );
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
