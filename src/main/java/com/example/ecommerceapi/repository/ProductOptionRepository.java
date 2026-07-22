package com.example.ecommerceapi.repository;

import com.example.ecommerceapi.model.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}