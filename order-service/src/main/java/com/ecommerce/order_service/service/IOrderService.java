package com.ecommerce.order_service.service;

import com.ecommerce.order_service.Enum.OrderStatus;
import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    Page<OrderResponse> getOrders (Pageable pageable, String userId, String status);
    OrderResponse getOrderById (int orderId);
    Page<OrderResponse> getOrdersByUserId (Pageable pageable,String userId, String status);
    OrderResponse addOrder (String userId);
    OrderResponse updateOrder (int orderId,OrderRequest orderRequest);
    void updateOrderStatus (int orderId, OrderStatus orderStatus);
    boolean deleteOrder (int orderId);
}
