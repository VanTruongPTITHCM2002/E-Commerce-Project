package com.ecommerce.order_service.controller;


import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.ApiResponse;
import com.ecommerce.order_service.dto.response.OrderResponse;
import com.ecommerce.order_service.service.IOrderService;
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
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    IOrderService iOrderService;

    @GetMapping
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

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> addOrder (@RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = this.iOrderService.addOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<OrderResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Created order successfully")
                        .data(orderResponse)
                        .build()
        );
    }
}
