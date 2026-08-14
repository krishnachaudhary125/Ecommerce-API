package com.example.ecommerceapi.repository;

import com.example.ecommerceapi.model.HotDealProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotDealsProductRepository extends JpaRepository<HotDealProducts, Long> {

    @Query("""
        SELECT h
        FROM HotDealProducts h
        ORDER BY h.product.discountPercentage DESC
    """
    )
    List<HotDealProducts> findAllOrderByDiscountPercentageDesc();
}
