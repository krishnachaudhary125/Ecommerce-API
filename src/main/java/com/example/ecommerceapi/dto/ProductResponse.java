package com.example.ecommerceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private String category;
    private String thumbnail;
    private Integer stock;

    private List<String> images;

    private Map<String, List<String>> options;

    private Double rating;

    private Integer reviewCount;

    private String brand;

    private Double discountPercentage;

    private Boolean featured;

    private Boolean active;
}