package com.ecommerce.order_service.service;

import com.ecommerce.order_service.Enum.OrderStatus;
import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.OrderResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    List<OrderResponse> getOrders (Pageable pageable);
    OrderResponse getOrderById (int orderId);
    List<OrderResponse> getOrdersByUserId (String userId);
    OrderResponse addOrder (String userId);
    OrderResponse updateOrder (int orderId,OrderRequest orderRequest);
    void updateOrderStatus (int orderId, OrderStatus orderStatus);
    boolean deleteOrder (int orderId);
}
