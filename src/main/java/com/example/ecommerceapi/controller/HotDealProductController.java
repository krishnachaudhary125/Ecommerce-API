package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.ProductResponse;
import com.example.ecommerceapi.service.HotDealProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hot-deal-products")
@RequiredArgsConstructor
public class HotDealProductController {

    private final HotDealProductService hotDealProductService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getHotDealProducts(){

        return ResponseEntity.ok(
                hotDealProductService.getHotDealProducts()
        );
    }
}
