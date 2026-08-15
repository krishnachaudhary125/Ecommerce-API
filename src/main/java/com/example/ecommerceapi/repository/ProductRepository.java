package com.example.ecommerceapi.repository;

import com.example.ecommerceapi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByTitleContainingIgnoreCase(String title);

    Page<Product> findByCategory_NameIgnoreCase(
            String categoryName,
            Pageable pageable
    );

    List<Product> findByFeaturedTrueAndActiveTrueOrderByIdDesc();

    List<Product> findByActiveTrueOrderByDiscountPercentageDesc();

    List<Product> findByActiveTrueOrderBySoldCountDesc();
}