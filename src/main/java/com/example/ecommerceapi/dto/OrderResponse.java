package com.example.ecommerceapi.dto;

import com.example.ecommerceapi.model.Order;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {

    private Long id;
    private String orderNumber;

    private Double subTotal;
    private Double shippingCharge;
    private Double tax;
    private Double totalAmount;

    private String paymentStatus;
    private String orderStatus;

    private String shippingFullName;
    private String shippingPhone;
    private String shippingProvince;
    private String shippingDistrict;
    private String shippingPostalCode;
    private String shippingAddressName;
    private String shippingLandmark;

    public static OrderResponse from(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())

                .subTotal(order.getSubtotal())
                .shippingCharge(order.getShippingCharge())
                .tax(order.getTax())
                .totalAmount(order.getTotalAmount())

                .paymentStatus(
                        order.getPaymentStatus().name()
                )
                .orderStatus(
                        order.getOrderStatus().name()
                )

                .shippingFullName(order.getShippingFullName())
                .shippingPhone(order.getShippingPhone())
                .shippingProvince(order.getShippingProvince())
                .shippingDistrict(order.getShippingDistrict())
                .shippingPostalCode(order.getShippingPostalCode())
                .shippingAddressName(order.getShippingAddressName())
                .shippingLandmark(order.getShippingLandmark())

                .build();
    }
}