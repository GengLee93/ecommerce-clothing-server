package com.example.Ecommerce_Clothing_Server.dto.response;

import com.example.Ecommerce_Clothing_Server.enums.RoleType;

public record JwtResponseDTO(
        String token,
        String email,
        RoleType role
) {}