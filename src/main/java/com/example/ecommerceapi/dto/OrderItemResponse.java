package com.example.ecommerceapi.dto;

import com.example.ecommerceapi.model.OrderItem;

public record OrderItemResponse(
        Long id,
        Long productId,
        String productTitle,
        String brand,
        String productImage,
        Double price,
        Integer quantity,
        Double subtotal
) {

    public static OrderItemResponse from(OrderItem item) {

        return new OrderItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProductTitle(),
                item.getBrand(),
                item.getProduct().getThumbnail(),
                item.getPrice(),
                item.getQuantity(),
                item.getSubtotal()
        );
    }
}