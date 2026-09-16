package com.example.ecommerceapi.dto;

import com.example.ecommerceapi.model.OrderItem;

public record OrderItemResponse(
        Long id,
        Long productId,
        String productTitle,
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
                item.getProduct().getThumbnail(),
                item.getPrice(),
                item.getQuantity(),
                item.getSubtotal()
        );
    }
}