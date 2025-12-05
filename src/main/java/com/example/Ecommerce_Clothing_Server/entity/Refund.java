package com.example.Ecommerce_Clothing_Server.entity;

import com.example.Ecommerce_Clothing_Server.enums.RefundStatusType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "refund_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_request_id", nullable = false)
    private Integer refundId;

    @Column(name = "status_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RefundStatusType statusType;

    @Column(name = "is_closed", nullable = false)
    private Boolean isClosed = false;

    @Column(name = "refund_reason", nullable = false, columnDefinition = "text")
    private String refundReason;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "vendor_response", columnDefinition = "text")
    private String vendorResponse;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "fk_order_item_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_refund_order_item_id")
    )
    private OrderItem orderItem;

}
