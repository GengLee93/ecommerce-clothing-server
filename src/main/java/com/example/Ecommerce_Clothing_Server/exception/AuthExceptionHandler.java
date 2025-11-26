package com.example.Ecommerce_Clothing_Server.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler extends RuntimeException {
    public AuthExceptionHandler(String message) {
        super(message);
    }
}