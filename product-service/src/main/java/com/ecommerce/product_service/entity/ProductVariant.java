package com.ecommerce.product_service.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product_variant")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariant extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(unique = true)
    String sku;

    @Column(name = "variant_name", unique = true)
    String variantName;

    @Column(name = "price",nullable = false, precision = 15, scale = 2)
    BigDecimal price;

    @Column(name = "original_price", precision = 15, scale = 2)
    BigDecimal originalPrice;

    @Column(name = "cost_price", precision = 15, scale = 2)
    BigDecimal costPrice;

    @Column(name = "quantity", nullable = false, precision = 15, scale = 2)
    BigDecimal stockQuantity;

    @ElementCollection
    List<String> images = new ArrayList<>();

    @Type(JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    String attributes;
}
