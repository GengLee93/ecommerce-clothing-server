package com.example.Ecommerce_Clothing_Server.entity;

import com.example.Ecommerce_Clothing_Server.enums.DeliverType;
import com.example.Ecommerce_Clothing_Server.enums.PayMethod;
import com.example.Ecommerce_Clothing_Server.enums.PayStatus;
import com.example.Ecommerce_Clothing_Server.enums.ShipStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
@NoArgsConstructor      // JPA 一定要
@AllArgsConstructor
@Builder
@ToString(exclude = "customer")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Column(name = "order_date" , nullable = false, updatable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Column(name = "payment_method", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private PayMethod paymentMethod;

    @Column(name = "credit_card_last_four", nullable = true, length = 5)
    private String creditCardLastFour;

    @Column(name = "ship_status", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ShipStatus shipStatus;

    @Column(name = "pay_status", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @Column(name = "deliver_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private DeliverType deliverType;

    @Column(name = "shipping_address", nullable = true, length = 200)
    private String shippingAddress;

    @Column(name = "pickup_store",nullable = true, length = 100)
    private String pickupStore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_customer_id", nullable = false)
    private Customer customer;

}
