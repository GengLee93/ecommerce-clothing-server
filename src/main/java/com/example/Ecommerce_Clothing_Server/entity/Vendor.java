package com.example.Ecommerce_Clothing_Server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vendor")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "store_address", length = 200)
    private String storeAddress;

    @Column(name = "store_description", length = 500)
    private String storeDescription;

    @Column(name = "store_logo_url", length = 500)
    private String storeLogoUrl;

    @Column(name = "payment_account", length = 100)
    private String paymentAccount;

    public Vendor() {}

    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getStoreAddress() { return storeAddress; }
    public void setStoreAddress(String storeAddress) { this.storeAddress = storeAddress; }
    public String getStoreDescription() { return storeDescription; }
    public void setStoreDescription(String storeDescription) { this.storeDescription = storeDescription; }
    public String getStoreLogoUrl() { return storeLogoUrl; }
    public void setStoreLogoUrl(String storeLogoUrl) { this.storeLogoUrl = storeLogoUrl; }
    public String getPaymentAccount() { return paymentAccount; }
    public void setPaymentAccount(String paymentAccount) { this.paymentAccount = paymentAccount; }
}
