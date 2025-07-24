package com.ecommerce.cart_service.mapper;

import com.ecommerce.cart_service.dto.request.CartRequest;
import com.ecommerce.cart_service.dto.response.CartResponse;
import com.ecommerce.cart_service.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {

    Cart toEntity (CartRequest cartRequest);
    @Mapping(source = "cartItemList", target = "cartItemResponseList")
    @Mapping(target = "status", expression = "java(cart.getStatus().name())")
    CartResponse toResponse (Cart cart);
}
