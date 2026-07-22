package com.example.ecommerceapi.repository;

import com.example.ecommerceapi.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}