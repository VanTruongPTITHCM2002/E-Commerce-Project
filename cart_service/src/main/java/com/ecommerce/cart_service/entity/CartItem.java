package com.ecommerce.cart_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartFK", referencedColumnName = "cartId", nullable = false)
    Cart cart;

    @Column(name = "product_id", nullable = false)
    String productId;

    @Column(name = "price")
    int price;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "sub_total")
    int subTotal;

}
