package com.example.ecommerceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeResponse {

    private List<ProductResponse> featuredProducts;

    private List<ProductResponse> hotDeals;

    private List<ProductResponse> popularBrandProducts;
}