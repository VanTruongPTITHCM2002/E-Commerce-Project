package com.ecommerce.product_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "brand")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Brand extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Column(length = 100, nullable = false)
    String name;
    @Column(length = 100, nullable = false)
    String slug;
    @Column(columnDefinition = "TEXT")
    String logoUrl;
    @Column(columnDefinition = "TEXT")
    String description;

    @OneToMany(mappedBy = "brand")
    List<Product> products = new ArrayList<>();
}
