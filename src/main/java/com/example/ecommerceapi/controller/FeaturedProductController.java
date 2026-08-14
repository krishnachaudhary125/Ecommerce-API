package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.ProductResponse;
import com.example.ecommerceapi.service.FeaturedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/featured-products")
@RequiredArgsConstructor
public class FeaturedProductController {

    private final FeaturedProductService featuredProductService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getFeaturedProducts() {

        System.out.println("🔥 FEATURED CONTROLLER CALLED");

        return ResponseEntity.ok(
                featuredProductService.getFeaturedProducts()
        );
    }
}
