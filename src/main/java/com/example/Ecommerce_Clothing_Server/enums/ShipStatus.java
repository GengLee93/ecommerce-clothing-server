package com.example.Ecommerce_Clothing_Server.enums;

import lombok.Getter;

@Getter
public enum ShipStatus {
    PENDING("Pending"),
    SHIPPED("Shipped"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled"),
    REFUND_REQUESTED("Refund Requested"),
    COMPLETED("Completed");

    private final String status;

    ShipStatus(String status) {
        this.status = status;
    }
}