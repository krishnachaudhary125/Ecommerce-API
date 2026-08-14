package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.ProductResponse;
import com.example.ecommerceapi.service.PopularBrandProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/popular-brand-products")
@RequiredArgsConstructor
public class PopularBrandProductController {

    private final PopularBrandProductService popularBrandProductService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getPopularBrandProduct() {

        return ResponseEntity.ok(
                popularBrandProductService.getPopularBrandProducts()
        );
    }
}
