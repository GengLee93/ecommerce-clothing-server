package com.example.Ecommerce_Clothing_Server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vendor")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Long vendorId;

    @Setter
    @Getter
    @OneToOne
    @JoinColumn(name = "fk_user_id", referencedColumnName = "user_id", unique = true)
    private User user;

    @Getter
    @Setter
    @Column(name = "store_address", length = 200)
    private String storeAddress;

    @Getter
    @Setter
    @Column(name = "store_description", length = 500)
    private String storeDescription;

    @Getter
    @Setter
    @Column(name = "store_logo_url", length = 500)
    private String storeLogoUrl;

    @Getter
    @Setter
    @Column(name = "payment_account", length = 100)
    private String paymentAccount;

    public Vendor() {}
}
