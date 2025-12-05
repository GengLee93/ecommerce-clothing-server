package com.example.Ecommerce_Clothing_Server.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "variant_id", nullable = false)
    private Long variantId;

    @Column(nullable = false, length = 500)
    private String url;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Column
    private Boolean cover = false;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    //Getter / Setter

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Long getVariantId() {return variantId;}

    public void setVariantId(Long variantId) {this.variantId = variantId;}

    public String getUrl() {return url;}

    public void setUrl(String url) {this.url = url;}

    public Integer getSortOrder() {return sortOrder;}

    public void setSortOrder(Integer sortOrder) {this.sortOrder = sortOrder;}

    public Boolean getCover() {return cover;}

    public void setCover(Boolean cover) {this.cover = cover;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
}
