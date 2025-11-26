package com.example.Ecommerce_Clothing_Server.enums;

import lombok.Getter;

@Getter
public enum DeliverType {
    // 常見台灣電商配送方式（完全對應真實物流）
    HOME_DELIVERY("宅配到府"),
    STORE_PICKUP_711("7-11 超商取貨"),
    STORE_PICKUP_FAMI("全家超商取貨");

    private final String type;        // 存進資料庫的英文（統一、不會亂）

    DeliverType(String type) {
        this.type = type;
    }
}