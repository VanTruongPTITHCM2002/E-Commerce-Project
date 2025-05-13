package com.ecommerce.order_service.service.impl;

import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.OrderResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.mapper.OrderMapper;
import com.ecommerce.order_service.repository.OrderRepository;
import com.ecommerce.order_service.service.IOrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements IOrderService {

    OrderRepository orderRepository;
    OrderMapper orderMapper;

    @Override
    public List<OrderResponse> getOrders(Pageable pageable) {
        return this.orderRepository.findAll().stream().map(
                orderMapper::toResponse
        ).toList();
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
        Order order = this.orderMapper.toEntity(orderRequest);
        order = this.orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse updateOrder(OrderRequest orderRequest) {
        Order order = this.orderMapper.toEntity(orderRequest);
        order = this.orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Override
    public boolean deleteOrder(int orderId) {
        return false;
    }
}
