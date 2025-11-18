package com.example.Ecommerce_Clothing_Server.enums;

import lombok.Getter;

@Getter
public enum PayStatus {
    PENDING("Pending"),
    PAID("Paid"),
    CANCELLED("Cancelled");
    private final String type;

    PayStatus(String type) {
        this.type = type;
    }
}
