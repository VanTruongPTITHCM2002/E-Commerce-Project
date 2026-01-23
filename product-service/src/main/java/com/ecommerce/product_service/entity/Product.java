package com.ecommerce.product_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@DynamicInsert
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    UUID id;

    @Column(name = "sku", unique = true, length = 64)
    String sku;

    @Column(name = "product_name",nullable = false,length = 100)
    String name;

    @Column(name = "slug", unique = true, length = 200)
    String slug;

    @Column(name = "thumbnail")
    String thumbnail;

    @ElementCollection
    @CollectionTable(name = "product_image", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url", length =  500)
    List<String> images = new ArrayList<>();

    @Column(name = "price",nullable = false, precision = 15, scale = 2)
    BigDecimal price;

    @Column(name = "original_price", precision = 15, scale = 2)
    BigDecimal originalPrice;

    @Column(name = "cost_price", precision = 15, scale = 2)
    BigDecimal costPrice;

    @Column(name = "quantity", nullable = false, precision = 15, scale = 2)
    BigDecimal quantity;

    @Column(name = "rating")
    BigDecimal rating;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "short_description", columnDefinition = "TEXT")
    String shortDescription;

    @Column(name = "review_count")
    Integer reviewCount = 0;

    @Column(name = "is_deleted")
    boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    Brand brand;

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    List<ProductVariant> productVariants = new ArrayList<>();
}
