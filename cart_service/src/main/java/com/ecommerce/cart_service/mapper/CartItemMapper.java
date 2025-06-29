package com.ecommerce.cart_service.mapper;

import com.ecommerce.cart_service.dto.response.CartItemResponse;
import com.ecommerce.cart_service.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemResponse toResponse (CartItem cartItem);
}
