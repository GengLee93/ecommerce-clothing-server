package com.example.Ecommerce_Clothing_Server.entity;

import com.example.Ecommerce_Clothing_Server.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
public class User {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Setter
    @Getter
    @Column(name = "username", length = 50)
    private String username;

    @Setter
    @Getter
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Setter
    @Getter
    @Column(name = "email", nullable = false, length = 200)
    private String email;

    @Getter
    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    @Setter
    @Getter
    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Getter
    @Setter
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, length = 20)
    private RoleType userType;

    public User() {}

    public String getUserType() { return userType.getRole(); }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) { this.createdAt = OffsetDateTime.now(); }
        if (this.isActive == null) { this.isActive = true; }
    }
}
