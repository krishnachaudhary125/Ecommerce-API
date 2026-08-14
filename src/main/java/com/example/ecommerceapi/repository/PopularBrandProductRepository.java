package com.example.ecommerceapi.repository;

import com.example.ecommerceapi.model.PopularBrandProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopularBrandProductRepository extends JpaRepository<PopularBrandProducts, Long> {

    List<PopularBrandProducts> findAllByOrderedByCreatedAtDesc();
}
