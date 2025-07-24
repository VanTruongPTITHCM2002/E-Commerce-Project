package com.ecommerce.auth_service.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessageFailure {
    USER_NOT_FOUND("User not found."),
    USER_EXISTS("Username or email already exists."),
    USER_UPDATE_FAILED("Failed to update user profile."),
    USER_DELETE_FAILED("Failed to delete user profile"),
    INCORRECT_OLD_PASSWORD("Current password is incorrect."),
    CONFIRM_NEW_PASSWORD_FAIL("New or confirm password not match"),
    LOGIN_FAILED("Invalid username or password."),
    UNAUTHORIZED("Unauthorized access. Please log in."),
    FORBIDDEN("Access denied. You do not have permission to perform this action."),
    ADMIN_ONLY("Only administrators are allowed to access this resource."),
    UNAUTHORIZED_USER_ACCESS("You are not authorized to access this user's information."),
    INSUFFICIENT_ROLE("You do not have the required role to perform this action."),
    ACCOUNT_DISABLED("Your account has been disabled or banned."),
    ROLE_NOT_FOUND("Role not found"),
    ROLE_ALREADY_EXISTS("Role already exists"),
    ROLE_DELETE_FAILED("Failed to delete role"),
    PERMISSION_NOT_FOUND("Permission not found"),
    PERMISSION_ALREADY_EXISTS("Permission already exists"),
    PERMISSION_DELETE_FAILED("Failed to delete permission"),
    ACCESS_DENIED("You do not have permission to perform this action");
    private final String message;
}
