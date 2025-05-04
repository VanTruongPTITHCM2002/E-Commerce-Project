package com.ecommerce.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int roleId;

    @Column(name = "rolename",nullable = false, unique = true)
    String roleName;

    @Column(name = "description")
    String description;

    @Column(name = "active")
    short active;

    @Column(name = "creatat",nullable = false)
    LocalDateTime createAt;

    @Column(name = "updateat")
    LocalDateTime updateAt;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL,orphanRemoval = true)
    List<User> users;

    @ManyToMany
    Set<Permission> permissions;
}
