package com.example.ecommerceapi.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponse {
    private Long productId;
    private String title;
    private String thumbnail;
    private Double price;
    private Integer quantity;

    public CartItemResponse(
            Long productId,
            String title,
            String thumbnail,
            Double price,
            Integer quantity
    ) {
        this.productId = productId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.price = price;
        this.quantity = quantity;
    }
}