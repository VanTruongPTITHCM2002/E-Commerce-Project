package com.ecommerce.cart_service.repository;

import com.ecommerce.cart_service.common.CartStatus;
import com.ecommerce.cart_service.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUserIdAndStatus (String userId, CartStatus status);
}
