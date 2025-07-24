package com.ecommerce.auth_service.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessageSuccess {
    LOGIN_SUCCESS("Login successfully"),
    LOGOUT_SUCCESS("Logout successfully"),
    REGISTER_SUCCESS("Register successfully"),
    USER_CREATED("User account created successfully"),
    USER_UPDATED("User profile updated successfully"),
    USER_DELETED("User account deleted successfully"),
    USERS_LISTED("Users retrieved successfully"),
    USER_LISTED("User retrieved successfully"),
    PASSWORD_CHANGED("Password changed successfully."),
    PASSWORD_RESET("Password reset successfully."),
    ROLE_CREATED("Role created successfully"),
    ROLE_UPDATED("Role updated successfully"),
    ROLE_DELETED("Role deleted successfully"),
    ROLE_FOUND("Role retrieved successfully"),
    ROLES_LISTED("Roles retrieved successfully"),
    PERMISSION_CREATED("Permission created successfully"),
    PERMISSION_UPDATED("Permission updated successfully"),
    PERMISSION_DELETED("Permission deleted successfully"),
    PERMISSION_FOUND("Permission retrieved successfully"),
    PERMISSIONS_LISTED("Permissions retrieved successfully");
    private final String message;
}
