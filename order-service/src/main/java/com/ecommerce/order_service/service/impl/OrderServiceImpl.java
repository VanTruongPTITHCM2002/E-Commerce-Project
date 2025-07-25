package com.ecommerce.order_service.service.impl;

import com.ecommerce.order_service.Enum.NotificationType;
import com.ecommerce.order_service.Enum.OrderStatus;
import com.ecommerce.order_service.common.MessageError;
import com.ecommerce.order_service.dto.request.NotificationRequest;
import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.CartResponse;
import com.ecommerce.order_service.dto.response.OrderResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.entity.OrderItem;
import com.ecommerce.order_service.event.CartServiceClient;
import com.ecommerce.order_service.event.NotificationServiceClient;
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

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements IOrderService {

    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    CartServiceClient cartServiceClient;
    OrderMapper orderMapper;
    NotificationServiceClient notificationServiceClient;

    @Override
    public List<OrderResponse> getOrders(Pageable pageable) {
        return this.orderRepository.findAll(pageable).stream().map(
                orderMapper::toResponse
        ).toList();
    }

    @Override
    public OrderResponse getOrderById(int orderId) {
        Order order = this.getOrderByOrderId(orderId);
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
    public OrderResponse addOrder(String userId) {
        CartResponse cartResponse = this.cartServiceClient.getCartByUser(userId).getData();
        Order order = new Order();
        order.setUserId(userId);
        order.setCreateAt(cartResponse.getCreateAt().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        order.setTotalPrice(cartResponse.getTotal());
        order.setStatus(OrderStatus.CREATED);
        List <OrderItem> items = cartResponse.getCartItemResponseList().stream().map(
                c -> OrderItem.builder()
                        .name(c.getName())
                        .price(BigDecimal.valueOf(c.getPrice()))
                        .quantity(c.getQuantity())
                        .subTotal(BigDecimal.valueOf(c.getSubTotal()))
                        .order(order)
                        .build()).toList();
        order.setOrderItemList(items);
        this.orderRepository.save(order);

        this.cartServiceClient.updateStatusCart(userId, true);
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse updateOrder(int orderId, OrderRequest orderRequest) {
        Order order = this.getOrderByOrderId(orderId);
        orderMapper.toUpdate(order,orderRequest);
        return orderMapper.toResponse(order);
    }

    @Override
    public void updateOrderStatus(int orderId, OrderStatus orderStatus) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND.value(), MessageError.ORDER_NOT_FOUND.getMessage())
        );

        order.setStatus(orderStatus);
        this.notificationServiceClient.sendNotification(NotificationRequest.builder()
                        .userId(order.getUserId())
                        .title("Order")
                        .message("Order #" + order.getOrderId() + " " + orderStatus.getName())
                        .type(NotificationType.MULTI)
                .build());
        this.orderRepository.save(order);
    }

    @Override
    public boolean deleteOrder(int orderId) {
        Order order = this.getOrderByOrderId(orderId);
        try{
            order.setStatus(OrderStatus.CANCELLED);
            this.orderRepository.save(order);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private Order getOrderByOrderId (int orderId){
        return this.orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND.value(), MessageError.ORDER_NOT_FOUND.getMessage())
        );
    }
}
