package com.example.ecommerceapi.service;

import com.example.ecommerceapi.dto.CategoryResponse;
import com.example.ecommerceapi.dto.ProductResponse;
import com.example.ecommerceapi.repository.PopularBrandProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularBrandProductService {

    private final PopularBrandProductRepository popularBrandProductRepository;

    public List<ProductResponse> getPopularBrandProducts(){

        return popularBrandProductRepository
                .findAllByOrderedByCreatedAtDesc()
                .stream()
                .map(popularBrand ->{
                    var product = popularBrand.getProduct();

                    CategoryResponse category = new CategoryResponse(
                            product.getCategory().getId(),
                            product.getCategory().getName()
                    );

                    return new ProductResponse(
                            product.getId(),
                            product.getTitle(),
                            product.getDescription(),
                            product.getPrice(),
                            category,
                            product.getThumbnail(),
                            product.getStock(),
                            null,
                            null,
                            product.getRating(),
                            product.getReviewCount(),
                            product.getBrand(),
                            product.getDiscountPercentage(),
                            true,
                            null
                    );
                })
                .toList();
    }
}
