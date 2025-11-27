package com.example.Ecommerce_Clothing_Server.enums;

public enum ExceptionCode {
    AUTH_FAILED(2001, "Authentication failed"),
    USER_NOT_FOUND(2002, "User not found"),
    INVALID_CREDENTIALS(2003, "Invalid credentials");

    private final int code;
    private final String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() { return code; }
    public String getMessage() { return message; }
}
