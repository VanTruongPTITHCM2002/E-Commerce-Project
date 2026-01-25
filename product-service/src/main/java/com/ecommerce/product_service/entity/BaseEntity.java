package com.ecommerce.product_service.entity;

import com.ecommerce.product_service.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {
    @Column( name = "created_at",updatable = false)
    ZonedDateTime createdAt;

    @Column(name = "updated_at")
    ZonedDateTime updatedAt;

    @Column(name = "created_by", updatable = false)
    String createdBy;

    @Column(name = "updated_by")
    String updatedBy;

    @Column(name = "deleted_at")
    ZonedDateTime deletedAt;

    @Column(name = "deleted_by")
    String deletedBy;

    EntityStatus entityStatus;

    @Version
    Long version;

    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now();
        updatedAt = ZonedDateTime.now();
        this.entityStatus = EntityStatus.ACTIVE;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }
}
