package com.ecommerce.order_service.mapper;

import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.OrderResponse;
import com.ecommerce.order_service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity (OrderRequest orderRequest);
    OrderResponse toResponse (Order order);
    Order toUpdate (@MappingTarget Order oder, OrderRequest orderRequest);
}
