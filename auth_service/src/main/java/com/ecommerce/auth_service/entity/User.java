package com.ecommerce.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String userId;

    @Column(name = "username",length = 50,nullable = false)
    String username;

    @Column(name = "password",length = 255,nullable = false)
    String password;

    @Column(name = "firstname", length = 50)
    String firstName;

    @Column(name = "lastname",length = 50)
    String lastName;

    @Column(name = "phonenumber",length = 11,unique = true)
    String phoneNumber;

    @Column(name = "email",length = 100)
    String email;

    @Column(name = "registerat")
    LocalDateTime registeredAt;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "roleFK",referencedColumnName = "roleId",nullable = false)
    Role role;
}
