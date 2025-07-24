package com.ecommerce.payment_service.entity;

import com.ecommerce.payment_service.common.PaymentMethod;
import com.ecommerce.payment_service.common.PaymentStatus;
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
@Builder
@DynamicUpdate
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String paymentId;

    @Column(name = "transaction_id")
    String transactionId;

    @Column(name = "order_id", nullable = false)
    int orderId;

    @Column(name = "total", nullable = false)
    int total;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    @Column(name = "create_at", nullable = false)
    LocalDateTime createAt;

    @Column(name = "update_at")
    LocalDateTime updateAt;

}
