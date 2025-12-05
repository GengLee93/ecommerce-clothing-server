package com.example.Ecommerce_Clothing_Server.repository;

import com.example.Ecommerce_Clothing_Server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
