package com.example.ecommerceapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyEsewaPaymentRequest {

    @NotBlank(message = "eSewa reference ID is required")
    private String refId;
}