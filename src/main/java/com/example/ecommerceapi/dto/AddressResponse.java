package com.example.ecommerceapi.dto;

import lombok.Data;

@Data
public class AddressResponse {

    private Long id;

    private Long userId;

    private String fullName;

    private String phone;

    private String addressName;

    private String formattedAddress;

    private Boolean defaultAddress;

    private Boolean billingAddress;

    private String label;
}
