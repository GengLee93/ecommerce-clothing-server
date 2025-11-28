package com.example.Ecommerce_Clothing_Server.enums;

public enum RoleType {
    CUSTOMER("CUSTOMER"),
    ADMIN("ADMIN"),
    VENDOR("VENDOR");

    final String role;
    RoleType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}