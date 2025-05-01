package com.ecommerce.product_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "product")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID product_id;

    @Column(name = "product_name",nullable = false,length = 100)
    private String name;

    @Column(name = "price",nullable = false)
    private int price;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
