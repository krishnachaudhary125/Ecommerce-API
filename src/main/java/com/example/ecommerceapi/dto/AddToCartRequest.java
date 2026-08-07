package com.example.ecommerceapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {

    private Long productId;
    private Integer quantity = 1;
}