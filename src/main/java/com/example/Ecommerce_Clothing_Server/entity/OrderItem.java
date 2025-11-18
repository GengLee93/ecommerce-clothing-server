package com.example.Ecommerce_Clothing_Server.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"order", "productVariant"})
public class OrderItem {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "unit_price", nullable = false)
    private Integer unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_product_variant_id", nullable = false)
    private ProductVariant productVariant;
}
