package com.example.Ecommerce_Clothing_Server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "default_shipping_address", length = 200)
    private String defaultShippingAddress;

    @Column(name = "billing_address", length = 200)
    private String billingAddress;

    public Customer() {}

    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getDefaultShippingAddress() { return defaultShippingAddress; }
    public void setDefaultShippingAddress(String defaultShippingAddress) { this.defaultShippingAddress = defaultShippingAddress; }
    public String getBillingAddress() { return billingAddress; }
    public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }
}
