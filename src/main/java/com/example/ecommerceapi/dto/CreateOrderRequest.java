package com.example.ecommerceapi.dto;

import com.example.ecommerceapi.model.PaymentOption;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotNull(message = "Shipping address is required")
    private Long shippingAddressId;

    @NotNull(message = "Payment option is required")
    private PaymentOption paymentOption;
}