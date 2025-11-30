package com.example.Ecommerce_Clothing_Server.controller;

import com.example.Ecommerce_Clothing_Server.dto.request.auth.LoginRequestDTO;
import com.example.Ecommerce_Clothing_Server.dto.request.auth.RefreshTokenRequestDTO;
import com.example.Ecommerce_Clothing_Server.dto.request.auth.RegisterRequestDTO;
import com.example.Ecommerce_Clothing_Server.dto.response.ApiResponseDTO;
import com.example.Ecommerce_Clothing_Server.dto.response.JwtResponseDTO;
import com.example.Ecommerce_Clothing_Server.entity.RefreshToken;
import com.example.Ecommerce_Clothing_Server.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "認證", description = "用戶登入、註冊及檢查用戶是否存在 API")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "用戶登入")
    public ResponseEntity<ApiResponseDTO<JwtResponseDTO>> login(@RequestBody LoginRequestDTO loginRequestDTO)
            throws Exception{

        // 呼叫服務端驗證取得 JWT token
        JwtResponseDTO jwtResponse = authService.authenticate(
                loginRequestDTO.email(),
                loginRequestDTO.password()
        );
        return ResponseEntity.ok(ApiResponseDTO.success("登入成功", jwtResponse));
    }

    @PostMapping("/refresh")
    @Operation(summary = "使用 refresh token 換取新 access token（並 rotation refresh token）")
    public ResponseEntity<ApiResponseDTO<JwtResponseDTO>> refresh(@RequestBody RefreshTokenRequestDTO request){
        JwtResponseDTO jwtResponse = authService.refreshToken(request.refreshToken());
        return ResponseEntity.ok(ApiResponseDTO.success("刷新成功", jwtResponse));
    }

    @PostMapping("/logout")
    @Operation(summary = "設備登出")
    public ResponseEntity<Object> logout(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        authService.logoutSingleDevice(refreshTokenRequestDTO.refreshToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/exists")
    @Operation(summary = "檢查帳號是否存在")
    public ResponseEntity<ApiResponseDTO<Object>> exists(@RequestBody RegisterRequestDTO registerRequestDTO){
        return ResponseEntity.ok(new ApiResponseDTO<Object>(true, "用戶名可用", null));
    }

    @PostMapping("/register")
    @Operation(summary = "用戶註冊")
    public ResponseEntity<ApiResponseDTO<Object>> register(@RequestBody RegisterRequestDTO registerRequestDTO){
        return ResponseEntity.ok(new ApiResponseDTO<Object>(true, "註冊成功", null));
    }
}
