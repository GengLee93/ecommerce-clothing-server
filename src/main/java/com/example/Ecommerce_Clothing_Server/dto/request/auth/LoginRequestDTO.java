package com.example.Ecommerce_Clothing_Server.dto.request.auth;

import com.example.Ecommerce_Clothing_Server.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "信箱不能為空")
        @Email(message = "信箱格式不正確")
        String email,

        @NotBlank(message = "密碼不能為空")
        String password,

        RoleType role
) {
}
