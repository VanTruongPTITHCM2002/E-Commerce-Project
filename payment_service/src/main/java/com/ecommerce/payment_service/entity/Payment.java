package com.ecommerce.payment_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Table
@Entity(name = "payment")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicInsert
@DynamicUpdate
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String paymentId;

    @Column(name = "order_id")
    int orderId;

    @Column(name = "amount")
    int amount;

    @Column(name = "payment_method")
    String paymentMethod;

    String status;

    @Column(name = "create_at")
    LocalDateTime createAt;

    @Column(name = "update_at")
    LocalDateTime updateAt;

}
