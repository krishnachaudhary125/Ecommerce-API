package com.example.ecommerceapi.dto;

import com.example.ecommerceapi.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailResponse(
        Long id,
        String orderNumber,
        String orderStatus,
        String paymentStatus,
        String paymentOption,

        String shippingFullName,
        String shippingPhone,
        String shippingProvince,
        String shippingDistrict,
        String shippingPostalCode,
        String shippingAddressName,
        String shippingLandmark,

        Double subtotal,
        Double shippingCharge,
        Double tax,
        Double totalAmount,

        LocalDateTime createdAt,

        List<OrderItemResponse> products
) {

    public static OrderDetailResponse from(Order order) {

        List<OrderItemResponse> products = order.getItems()
                .stream()
                .map(OrderItemResponse::from)
                .toList();

        return new OrderDetailResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderStatus().name(),
                order.getPaymentStatus().name(),
                order.getPaymentOption().name(),

                order.getShippingFullName(),
                order.getShippingPhone(),
                order.getShippingProvince(),
                order.getShippingDistrict(),
                order.getShippingPostalCode(),
                order.getShippingAddressName(),
                order.getShippingLandmark(),

                order.getSubtotal(),
                order.getShippingCharge(),
                order.getTax(),
                order.getTotalAmount(),

                order.getCreatedAt(),

                products
        );
    }
}