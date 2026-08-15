package com.example.ecommerceapi.service;

import com.example.ecommerceapi.dto.HomeResponse;
import com.example.ecommerceapi.dto.ProductResponse;
import com.example.ecommerceapi.mapper.ProductMapper;
import com.example.ecommerceapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public HomeResponse getHome() {

        List<ProductResponse> featuredProducts =
                productRepository
                        .findByFeaturedTrueAndActiveTrueOrderByIdDesc()
                        .stream()
                        .map(productMapper::toResponse)
                        .toList();

        List<ProductResponse> hotDeals =
                productRepository
                        .findByActiveTrueOrderByDiscountPercentageDesc()
                        .stream()
                        .map(productMapper::toResponse)
                        .toList();

        List<ProductResponse> popularBrandProducts =
                productRepository
                        .findByActiveTrueOrderBySoldCountDesc()
                        .stream()
                        .map(productMapper::toResponse)
                        .toList();

        return new HomeResponse(
                featuredProducts,
                hotDeals,
                popularBrandProducts
        );
    }
}