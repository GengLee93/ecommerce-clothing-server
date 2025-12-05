package com.example.Ecommerce_Clothing_Server.service;

import com.example.Ecommerce_Clothing_Server.entity.User;
import com.example.Ecommerce_Clothing_Server.enums.ExceptionCode;
import com.example.Ecommerce_Clothing_Server.exception.SharedException;
import com.example.Ecommerce_Clothing_Server.repository.AuthRepository;
import com.example.Ecommerce_Clothing_Server.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final AuthRepository authRepository;

    public UserDetailServiceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginIdentifier) throws UsernameNotFoundException {
        User user = authRepository.findByEmail(loginIdentifier)
                .orElseThrow(() -> new SharedException("User not found", ExceptionCode.USER_NOT_FOUND));
        return new CustomUserDetails(user);
    }
}