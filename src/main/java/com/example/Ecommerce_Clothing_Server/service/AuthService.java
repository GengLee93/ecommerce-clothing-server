package com.example.Ecommerce_Clothing_Server.service;

import com.example.Ecommerce_Clothing_Server.dto.response.JwtResponseDTO;
import com.example.Ecommerce_Clothing_Server.enums.ExceptionCode;
import com.example.Ecommerce_Clothing_Server.enums.RoleType;
import com.example.Ecommerce_Clothing_Server.exception.SharedException;
import com.example.Ecommerce_Clothing_Server.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // 使用者登入，驗證帳號密碼並產生 JWT
    public JwtResponseDTO authenticate(String email, String password) {
        try {
            // 驗證帳號密碼
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // 查使用者資訊（用來產生 JWT）
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // 產生 JWT
            final String token = jwtService.generateToken(userDetails);
            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).findFirst().orElse("GUEST");
            return new JwtResponseDTO(token, email, RoleType.valueOf(role));

        } catch (BadCredentialsException e) {
            throw new SharedException("密碼錯誤", ExceptionCode.INVALID_CREDENTIALS, e);
        } catch (SharedException e) {
            throw e;
        } catch (Exception e) {
            throw new SharedException("驗證失敗", ExceptionCode.AUTH_FAILED, e);
        }
    }

    // 註冊使用者
    public void register(String email, String password, RoleType role) { }
//
//    // 檢查使用者是否存在
//    public boolean checkUserExists(String email, RoleType role) {}
//
//    // 回傳 userid
//    public Long getUserIdFromEmail(String email) {}
}