package com.ecommerce.product_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
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
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    UUID productId;

    @Column(name = "product_name",nullable = false,length = 100, unique = true)
    String name;

    @Column(name = "thumbnail")
    String thumbnail;

    @Column(name = "price",nullable = false)
    int price;

    @Column(name = "cost_price")
    int costPrice;

    @Column(name = "quantity", nullable = false)
    int quantity;

    @Column(name = "rating")
    double rating;

    @Column(name = "create_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @CreatedDate
    LocalDateTime createAt;

    @Column(name = "update_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDateTime updateAt;

    @PreUpdate
    public void onUpdate(){
        this.updateAt = LocalDateTime.now();
    }

    @Column(name = "is_deleted")
    boolean isDeleted = false;
}
