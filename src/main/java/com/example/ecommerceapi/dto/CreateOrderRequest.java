package com.example.ecommerceapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotNull(message = "Shipping address is required")
    private Long shippingAddressId;
}