package com.example.Ecommerce_Clothing_Server.exception;

import com.example.Ecommerce_Clothing_Server.enums.ExceptionCode;

// 拋出特定業務例外或是自訂例外
public class SharedException extends RuntimeException {
    private ExceptionCode exceptionCode;

    public SharedException(String message) {
        super(message);
    }

    public SharedException(String message, ExceptionCode errorCode) {
        super(message);
        this.exceptionCode = errorCode;
    }

    public SharedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SharedException(String message, ExceptionCode exceptionCode, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getErrorCode() {
        return exceptionCode;
    }
}