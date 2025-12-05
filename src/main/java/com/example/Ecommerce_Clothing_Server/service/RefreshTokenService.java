package com.example.Ecommerce_Clothing_Server.service;

import com.example.Ecommerce_Clothing_Server.entity.RefreshToken;
import com.example.Ecommerce_Clothing_Server.enums.ExceptionCode;
import com.example.Ecommerce_Clothing_Server.exception.SharedException;
import com.example.Ecommerce_Clothing_Server.repository.AuthRepository;
import com.example.Ecommerce_Clothing_Server.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenService {

    @Value("${jwt.refresh-expiration}")
    private long refreshDuration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthRepository authRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, AuthRepository authRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.authRepository = authRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    // 產生有效長期 Refresh Token
    @Transactional
    public RefreshToken createRefreshToken(String email) {
        // 支援多設備裝置登入
        var user = authRepository.findByEmail(email)
                .orElseThrow(() -> new SharedException("User not found", ExceptionCode.USER_NOT_FOUND));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshDuration * 1000));
        refreshToken.setToken(java.util.UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    // 驗證有效期限
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new SharedException("Refresh token was expired. Please log in again."
                    , ExceptionCode.REFRESH_TOKEN_EXPIRED);
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(authRepository.findById(userId).get());
    }

    @Transactional
    public void logoutSingleDevice(String refreshTokenValue) {
        int deleted = refreshTokenRepository.deleteByToken(refreshTokenValue);
        if (deleted == 0) {
            throw new SharedException("Refresh token not found", ExceptionCode.USER_NOT_FOUND);
        }
    }

    @Transactional
    public void logoutAllDevices(Long userId) {
        var user = authRepository.findById(userId)
                .orElseThrow(() -> new SharedException("User not found", ExceptionCode.USER_NOT_FOUND));
        refreshTokenRepository.deleteByUser(user);
    }
}