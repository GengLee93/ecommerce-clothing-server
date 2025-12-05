package com.example.Ecommerce_Clothing_Server.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // 使用 @Value 注入 application.yaml 中的 secret key 和過期時間
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities());
        claims.put("userId", userDetails.getUsername());
        return createToken(claims, userDetails.getUsername());
    }

    // 新增：直接使用 username 與 role 字串產生 token（refresh 流程使用）
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("userId", username);
        return createToken(claims, username);
    }

    // 產生 JWT Token，用使用者的 username 來當成 subject
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(getSigningKey())
                .compact();
    }

    // 驗證使用者傳來的 JWT 是不是合法的
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // 從 JWT 取出裡面的 Subject (使用者的 username)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 從 properties 取出 JWT 密鑰，轉換成 Java 的 SecretKey 物件
    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
    }

    // 從 JWT 取出裡面的 Expiration (過期時間)
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 取出 JWT 特定的 Claim
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 取出 JWT 的所有 Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 有沒有過期
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
