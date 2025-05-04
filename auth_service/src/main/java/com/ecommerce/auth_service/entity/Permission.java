package com.ecommerce.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int permissionId;

    @Column(name = "permissioname",nullable = false)
    String permissionName;

    @Column(name = "description")
    String description;

    @Column(name = "active")
    short active;

    @Column(name = "creatat",nullable = false)
    LocalDateTime createAt;

    @Column(name = "updateat")
    LocalDateTime updateAt;

}
