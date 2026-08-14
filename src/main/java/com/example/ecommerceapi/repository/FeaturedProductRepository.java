package com.example.ecommerceapi.repository;

import com.example.ecommerceapi.model.FeaturedProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeaturedProductRepository extends JpaRepository<FeaturedProducts, Long> {

    List<FeaturedProducts> findAllByOrderByCreatedAtDesc();
}

