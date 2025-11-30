package com.example.Ecommerce_Clothing_Server.service;

import com.example.Ecommerce_Clothing_Server.dto.response.JwtResponseDTO;
import com.example.Ecommerce_Clothing_Server.entity.RefreshToken;
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
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(
            JwtService jwtService,
            RefreshTokenService refreshTokenService,
            AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
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

            // 產生 JWT Access Token
            final String token = jwtService.generateToken(userDetails);

            // 產生 DB 持久 Refresh Token
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(email);

            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).findFirst().orElse("GUEST");

            return new JwtResponseDTO(token, refreshToken.getToken(), email, RoleType.valueOf(role));

        } catch (BadCredentialsException e) {
            throw new SharedException("密碼錯誤", ExceptionCode.INVALID_CREDENTIALS, e);
        } catch (SharedException e) {
            throw e;
        } catch (Exception e) {
            throw new SharedException("驗證失敗", ExceptionCode.AUTH_FAILED, e);
        }
    }

    // 刷新 Access token 並且 rotate refresh token
    public JwtResponseDTO refreshToken(String refreshTokenValue) {
        RefreshToken token = refreshTokenService.findByToken(refreshTokenValue)
                .orElseThrow(() -> new SharedException("Refresh token not found", ExceptionCode.USER_NOT_FOUND));

        // 驗證過期 (過期拋例外並刪除)
        refreshTokenService.verifyExpiration(token);

        // 由 token 取得 UserDetails
        var user = token.getUser();
        String email = user.getEmail();
        String role = user.getUserType();

        // 刪除舊的 refresh token (rotation)，並建立新的 refresh token
        refreshTokenService.logoutSingleDevice(refreshTokenValue);
        RefreshToken newRefresh = refreshTokenService.createRefreshToken(email);

        // 產生新的 access token (使用 JwtService 新增的 overload)
        String newAccess = jwtService.generateToken(email, role);

        return new JwtResponseDTO(newAccess, newRefresh.getToken(), email, RoleType.valueOf(role));
    }

    // 登出單一裝置
    public void logoutSingleDevice(String refreshTokenValue) {
        refreshTokenService.logoutSingleDevice(refreshTokenValue);
    }

    // 登出所有裝置
    public void logoutAllDevices(Long userId) {
        refreshTokenService.deleteByUserId(userId);
    }
}