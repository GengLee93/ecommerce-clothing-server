package com.example.Ecommerce_Clothing_Server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "fk_user_id", referencedColumnName = "user_id", unique = true)
    private User user;

    @Getter
    @Setter
    @Column(name = "default_shipping_address", length = 200)
    private String defaultShippingAddress;

    @Getter
    @Setter
    @Column(name = "billing_address", length = 200)
    private String billingAddress;

    public Customer() {}
}
