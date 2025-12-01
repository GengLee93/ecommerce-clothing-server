package com.example.Ecommerce_Clothing_Server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final UserDetailServiceImpl userDetailService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(
            JwtService jwtService,
            UserDetailServiceImpl userDetailService,
            AuthenticationManager authenticationManager
    ) {
        this.jwtService = jwtService;
        this.userDetailService = userDetailService;
        this.authenticationManager = authenticationManager;
    }

    public String authenticate(String email, String password, String role) {
        try {
            // 驗證帳號密碼
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // 查使用者資訊（用來產生 JWT）
            UserDetails userDetails = userDetailService.loadUserByUsername(email);

            // 產生 JWT
            return jwtService.generateToken(userDetails);

        } catch (UsernameNotFoundException e) {
            throw new RuntimeException("帳號不存在");
        } catch (BadCredentialsException e) {
            throw new RuntimeException("密碼錯誤");
        }

    }
}
