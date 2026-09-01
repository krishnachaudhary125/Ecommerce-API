package com.example.ecommerceapi.dto;

import lombok.Data;

@Data
public class AddressRequest {

    private String fullName;

    private String phone;

    private String addressName;

    private String formattedAddress;

    private Boolean isDefaultAddress;

    private Boolean isBillingAddress;

    private String label;
}
