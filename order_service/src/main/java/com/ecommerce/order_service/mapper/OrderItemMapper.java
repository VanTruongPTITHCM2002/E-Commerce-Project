package com.ecommerce.order_service.mapper;

import com.ecommerce.order_service.dto.request.OrderItemRequest;
import com.ecommerce.order_service.dto.response.OrderItemResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.entity.OrderItem;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toEntity (OrderItemRequest orderItemRequest);
    OrderItemResponse toResponse (Order order);
}
