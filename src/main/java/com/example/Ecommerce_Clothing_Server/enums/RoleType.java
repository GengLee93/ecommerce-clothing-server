package com.example.Ecommerce_Clothing_Server.enums;

public enum RoleType {
    ROLE_CUSTOMER("CUSTOMER"),
    ROLE_ADMIN("ADMIN"),
    ROLE_VENDOR("VENDOR");

    final String role;
    RoleType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}