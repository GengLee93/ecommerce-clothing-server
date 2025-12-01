package com.example.Ecommerce_Clothing_Server.service;

import com.example.Ecommerce_Clothing_Server.entity.User;
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
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginIdentifier));
        return new CustomUserDetails(user);
    }
}
