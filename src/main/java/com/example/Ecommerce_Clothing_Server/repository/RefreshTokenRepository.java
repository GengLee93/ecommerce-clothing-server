package com.example.Ecommerce_Clothing_Server.repository;

import com.example.Ecommerce_Clothing_Server.entity.RefreshToken;
import com.example.Ecommerce_Clothing_Server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Query("delete from refresh_tokens r where r.user = ?1")
    int deleteByUser(User user);

    @Modifying
    @Query("delete from refresh_tokens r where r.token = ?1")
    int deleteByToken(String token);
}
