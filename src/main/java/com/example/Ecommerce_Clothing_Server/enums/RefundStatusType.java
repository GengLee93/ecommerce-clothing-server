package com.example.Ecommerce_Clothing_Server.enums;

import lombok.Getter;

@Getter
public enum RefundStatusType {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String type;

    RefundStatusType(String type) {
        this.type = type;
    }
}
