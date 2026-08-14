package com.example.ecommerceapi.service;

import com.example.ecommerceapi.dto.CategoryResponse;
import com.example.ecommerceapi.dto.ProductResponse;
import com.example.ecommerceapi.repository.HotDealsProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotDealProductService {

    private final HotDealsProductRepository hotDealsProductRepository;

    public List<ProductResponse> getHotDealProducts(){

        return hotDealsProductRepository
                .findAllByOrderedByCreatedAtDesc()
                .stream()
                .map(hotDeal -> {
                    var product = hotDeal.getProduct();

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
                            null,
                            null
                    );
                })
                .toList();
    }
}
