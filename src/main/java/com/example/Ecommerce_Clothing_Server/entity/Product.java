package com.example.Ecommerce_Clothing_Server.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(precision = 3, scale = 2)
    private BigDecimal rate = BigDecimal.ZERO;

    @Column(name = "is_list")
    private Boolean isList = true;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    // Getter / Setter

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public BigDecimal getPrice() {return price;}

    public void setPrice(BigDecimal price) {this.price = price;}

    public String getStatus() { return status;}

    public void setStatus(String status) {this.status = status;}

    public BigDecimal getRate() {return rate;}

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Boolean getIsList() {return isList;}

    public void setIsList(Boolean isList) {this.isList = isList;}

    public Long getCategoryId() {return categoryId;}

    public void setCategoryId(Long categoryId) {this.categoryId = categoryId;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
}
