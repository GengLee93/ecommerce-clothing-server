package com.example.Ecommerce_Clothing_Server.enums;

public enum ExceptionCode {
    AUTH_FAILED(2001, "Authentication failed"),
    USER_NOT_FOUND(2002, "User not found"),
    INVALID_CREDENTIALS(2003, "Invalid credentials"),
    REFRESH_TOKEN_EXPIRED(2004, "Refresh token expired"),
    USER_ALREADY_EXISTS(2005, "User already exist"),
    INVALID_ROLE(2006, "Invalid role");

    private final int code;
    private final String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() { return code; }
    public String getMessage() { return message; }
}
