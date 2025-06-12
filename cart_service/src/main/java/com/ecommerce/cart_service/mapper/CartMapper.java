package com.ecommerce.cart_service.mapper;

import com.ecommerce.cart_service.dto.request.CartRequest;
import com.ecommerce.cart_service.dto.response.CartResponse;
import com.ecommerce.cart_service.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    Cart toEntity (CartRequest cartRequest);
    CartResponse toResponse (Cart cart);
}
