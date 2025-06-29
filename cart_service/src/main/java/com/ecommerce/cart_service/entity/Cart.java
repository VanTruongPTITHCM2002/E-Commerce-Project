package com.ecommerce.cart_service.entity;

import com.ecommerce.cart_service.common.CartStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int cartId;

    @Column(name = "userid", nullable = false)
    String userId;

    int total;

    @Column(name = "creatat")
    Date createAt;

    @Column(name = "updateat")
    Date updateAt;

    @Enumerated(EnumType.STRING)
    CartStatus status;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CartItem> cartItemList = new ArrayList<>();
}
