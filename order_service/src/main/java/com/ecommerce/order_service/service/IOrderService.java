package com.ecommerce.order_service.service;

import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.OrderResponse;

import java.util.List;

public interface IOrderService {
    List<OrderResponse> getOrders ();
    OrderResponse getOrderById (int orderId);
    List<OrderResponse> getOrdersByUserId (int userId);
    OrderResponse addOrder (OrderRequest orderRequest);
    OrderResponse updateOrder (OrderRequest orderRequest);
    boolean deleteOrder (int orderId);
}
