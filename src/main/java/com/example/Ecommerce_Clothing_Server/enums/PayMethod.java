package com.example.Ecommerce_Clothing_Server.enums;

import lombok.Getter;

@Getter
public enum PayMethod {
    CASH("Cash"),
    CARD("Card");

    private final String method;

    PayMethod(String method) {
        this.method = method;
    }
}