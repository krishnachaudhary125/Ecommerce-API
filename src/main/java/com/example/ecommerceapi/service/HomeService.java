package com.example.ecommerceapi.service;

import com.example.ecommerceapi.dto.HomeResponse;
import com.example.ecommerceapi.dto.ProductResponse;
import com.example.ecommerceapi.mapper.ProductMapper;
import com.example.ecommerceapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private static final int HOME_SECTION_SIZE = 10;

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public HomeResponse getHome() {

        Pageable featuredPageable =
                PageRequest.of(
                        0,
                        HOME_SECTION_SIZE,
                        Sort.by(Sort.Direction.DESC, "id")
                );

        Pageable hotDealPageable =
                PageRequest.of(
                        0,
                        HOME_SECTION_SIZE,
                        Sort.by(Sort.Direction.DESC, "discountPercentage")
                );

        Pageable popularPageable =
                PageRequest.of(
                        0,
                        HOME_SECTION_SIZE,
                        Sort.by(Sort.Direction.DESC, "soldCount")
                );

        List<ProductResponse> featuredProducts =
                productRepository
                        .findByFeaturedTrueAndActiveTrue(featuredPageable)
                        .stream()
                        .map(productMapper::toResponse)
                        .toList();

        List<ProductResponse> hotDeals =
                productRepository
                        .findByActiveTrue(hotDealPageable)
                        .stream()
                        .map(productMapper::toResponse)
                        .toList();

        List<ProductResponse> popularBrandProducts =
                productRepository
                        .findByActiveTrue(popularPageable)
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