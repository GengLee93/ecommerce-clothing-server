package com.example.Ecommerce_Clothing_Server.config;

import com.example.Ecommerce_Clothing_Server.service.JwtService;
import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

// 繼承 OncePerRequestFilter，確保每個請求只執行一次過濾
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    JwtRequestFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 從 Header 裡面取出 JWT，並且拿出 JWT 裡面的 username
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtService.extractUsername(jwt);
        }

        // 如果有 JWT (有 username)，而且是未登入狀態
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 使用 JWT 的 username 去找到使用者相關的資料，這裡是從資料庫裡面取出資料
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 驗證 JWT 是不是有效的
            if (jwtService.validateToken(jwt, userDetails)) {

                // 如果 JWT 是有效的，就設定到 Spring Security 的 Context，表示使用者已經登入
                var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
