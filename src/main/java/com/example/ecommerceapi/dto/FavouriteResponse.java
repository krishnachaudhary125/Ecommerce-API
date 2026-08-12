package com.example.ecommerceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavouriteResponse {
    private Long productId;
    private String title;
    private String thumbnail;
    private Double price;
    private String brand;
}
