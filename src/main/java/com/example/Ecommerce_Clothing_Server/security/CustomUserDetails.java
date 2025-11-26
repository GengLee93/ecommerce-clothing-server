package com.example.Ecommerce_Clothing_Server.security;

import com.example.Ecommerce_Clothing_Server.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.beans.Transient;
import java.util.Collection;
import java.util.List;

// 作為 Adapter 將 User 實體轉換成 Spring Security 能理解的 UserDetails
public record CustomUserDetails(User user) implements UserDetails {

    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getUserType();
        return List.of(new SimpleGrantedAuthority("ROLE" + role));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 使用者的唯一識別值，用來判斷身分，這邊使用 email 作為 username
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsActive();
    }

    public Long getId() {
        return user.getId();
    }

    // TODO: Implement method to get role
    public String getRole() {
        return getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_GUEST");
    }
}
//public class CustomUserDetails implements UserDetails {
//    private final User user;
//    public CustomUserDetails(User user) {
//        this.user = user;
//    }
//
//    @Transient
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        String role = user.getUserType().getRole();
//        return List.of(new SimpleGrantedAuthority("ROLE" + role));
//    }
//
//    @Override public String getPassword() { return user.getPassword(); }
//    @Override public String getUsername() { return user.getEmail(); }
//    @Override public boolean isAccountNonExpired() { return true; }
//    @Override public boolean isAccountNonLocked() { return true; }
//    @Override public boolean isCredentialsNonExpired() { return true; }
//    @Override public boolean isEnabled() { return user.getIsActive(); }
//
//    public Long getId() { return user.getId(); }
//    public User getUser() { return user; }
//
//    public String getRole() {
//        return getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .findFirst()
//                .orElse("ROLE_GUEST");
//    }
//}