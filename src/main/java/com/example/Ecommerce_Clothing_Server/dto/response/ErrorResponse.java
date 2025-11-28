package com.example.Ecommerce_Clothing_Server.dto.response;

public record ErrorResponse(
        int code,
        String message
) {
}
