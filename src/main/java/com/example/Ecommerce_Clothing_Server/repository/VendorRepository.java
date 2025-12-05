package com.example.Ecommerce_Clothing_Server.repository;

import com.example.Ecommerce_Clothing_Server.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
