package com.ecommerce.auth_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    int roleId;
    String roleName;
    String description;
    short active;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    Set<PermissionResponse> permissions;
}
