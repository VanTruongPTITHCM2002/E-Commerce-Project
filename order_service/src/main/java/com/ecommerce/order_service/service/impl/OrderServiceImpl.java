package com.ecommerce.order_service.service.impl;

import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.OrderResponse;
import com.ecommerce.order_service.repository.OrderRepository;
import com.ecommerce.order_service.service.IOrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements IOrderService {

    OrderRepository orderRepository;

    @Override
    public List<OrderResponse> getOrders() {
        return List.of();
    }

    @Override
    public OrderResponse getOrderById(int orderId) {
        return null;
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(int userId) {
        return List.of();
    }

    @Override
    public OrderResponse addOrder(OrderRequest orderRequest) {
        return null;
    }

    @Override
    public OrderResponse updateOrder(OrderRequest orderRequest) {
        return null;
    }

    @Override
    public boolean deleteOrder(int orderId) {
        return false;
    }
}
