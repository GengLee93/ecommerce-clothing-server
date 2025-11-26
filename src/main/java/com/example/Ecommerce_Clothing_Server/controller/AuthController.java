package com.example.Ecommerce_Clothing_Server.controller;

import com.example.Ecommerce_Clothing_Server.dto.request.auth.LoginRequestDTO;
import com.example.Ecommerce_Clothing_Server.dto.request.auth.RegisterRequestDTO;
import com.example.Ecommerce_Clothing_Server.dto.response.ApiResponseDTO;
import com.example.Ecommerce_Clothing_Server.dto.response.JwtResponseDTO;
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

    private AuthService authService;

    @Autowired
    public void SetAuthService(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "用戶登入")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "註冊成功"),
            @ApiResponse(responseCode = "400", description = "用戶名已存在或資料格式錯誤")
    })
    public ResponseEntity<ApiResponseDTO<JwtResponseDTO>> login(@RequestBody LoginRequestDTO loginRequestDTO)
            throws Exception{
        return ResponseEntity.ok(new ApiResponseDTO<JwtResponseDTO>(true, "登入成功", null));
    }

    @PostMapping("/exists")
    @Operation(summary = "檢查用戶名是否可用")
    public ResponseEntity<ApiResponseDTO<Object>> exists(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(new ApiResponseDTO<Object>(true, "用戶名可用", null));
    }

    @PostMapping("/register")
    @Operation(summary = "用戶註冊")
    public ResponseEntity<ApiResponseDTO<Object>> register(@RequestBody RegisterRequestDTO registerRequestDTO){
        return ResponseEntity.ok(new ApiResponseDTO<Object>(true, "註冊成功", null));
    }
}
