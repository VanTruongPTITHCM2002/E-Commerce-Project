package com.ecommerce.order_service.service.impl;

import com.ecommerce.order_service.Enum.OrderStatus;
import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.OrderResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.exception.AppException;
import com.ecommerce.order_service.mapper.OrderMapper;
import com.ecommerce.order_service.repository.OrderItemRepository;
import com.ecommerce.order_service.repository.OrderRepository;
import com.ecommerce.order_service.service.IOrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements IOrderService {

    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    OrderMapper orderMapper;

    @Override
    public List<OrderResponse> getOrders(Pageable pageable) {
        return this.orderRepository.findAll(pageable).stream().map(
                orderMapper::toResponse
        ).toList();
    }

    @Override
    public OrderResponse getOrderById(int orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND.value(), "Order not found")
        );
        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(String userId) {
        return this.orderRepository.findByUserId(userId)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse addOrder(OrderRequest orderRequest) {
        Order order = this.orderMapper.toEntity(orderRequest);
        order = this.orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse updateOrder(int orderId, OrderRequest orderRequest) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("Order not found")
        );
        orderMapper.toUpdate(order,orderRequest);
        return orderMapper.toResponse(order);
    }

    @Override
    public boolean deleteOrder(int orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND.value(), "Order not found")
        );
        try{
            order.setStatus(OrderStatus.CANCELLED);
            this.orderRepository.save(order);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
