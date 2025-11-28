package com.example.Ecommerce_Clothing_Server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class Admin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @OneToOne
    @JoinColumn(name = "fk_user_id", referencedColumnName = "user_id", unique = true)
    private User user;

    public Admin() {}
}
