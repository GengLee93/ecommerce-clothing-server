package com.example.Ecommerce_Clothing_Server.config;

import com.example.Ecommerce_Clothing_Server.repository.AuthRepository;
import com.example.Ecommerce_Clothing_Server.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        // swagger-ui 和 api-docs 允許所有人訪問
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // 允許所有人訪問 api/auth 下面的 API
                        .requestMatchers("/api/auth/**").permitAll()
                        // 允許所有人訪問商品頁面
                        .requestMatchers("/api/products/**").permitAll()       // 商品列表、詳情
                        // 允許所有人訪問購物車頁面
                        .requestMatchers("/api/cart/guest/**").permitAll()      // 訪客購物車（前端儲存或 cookie）
                        // 要求所有其他請求都必須經過認證
                        .anyRequest().authenticated()
                )
                // 設定 session 管理策略為無狀態（STATELESS）
                // 表示應用程式不會為每個使用者儲存相關的 session，這是基於令牌的認證方案 (JWT) 的典型設定
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 啟用匿名使用者訪問
                .anonymous(Customizer.withDefaults())
                // 設定 JWT 請求過濾器，來驗證 JWT，並將其放置在 UsernamePasswordAuthenticationFilter 之前
                // 確保了 JWT 的驗證在使用者名密碼驗證之前進行
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 建立一個 AuthenticationManager bean
    // AuthenticationManager 是 Spring Security 用於處理認證請求的核心功能
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // 使用 AuthenticationConfiguration 來取得預設的 AuthenticationManager
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}