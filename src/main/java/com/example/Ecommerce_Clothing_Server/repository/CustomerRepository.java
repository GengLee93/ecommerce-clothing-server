package com.example.Ecommerce_Clothing_Server.repository;

import com.example.Ecommerce_Clothing_Server.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
