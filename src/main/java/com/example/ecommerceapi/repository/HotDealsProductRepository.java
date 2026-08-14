package com.example.ecommerceapi.repository;

import com.example.ecommerceapi.model.HotDealProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotDealsProductRepository extends JpaRepository<HotDealProducts, Long> {

    List<HotDealProducts> findAllByOrderedByCreatedAtDesc();
}
