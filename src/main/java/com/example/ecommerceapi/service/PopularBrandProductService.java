package com.example.ecommerceapi.service;

import com.example.ecommerceapi.dto.CategoryResponse;
import com.example.ecommerceapi.dto.ProductResponse;
import com.example.ecommerceapi.mapper.ProductMapper;
import com.example.ecommerceapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularBrandProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> getPopularBrandProducts() {

        return productRepository
                .findByActiveTrueOrderBySoldCountDesc()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }
}
