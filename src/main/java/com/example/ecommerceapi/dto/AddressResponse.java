package com.example.ecommerceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressResponse {

    private Long id;

    private String fullName;

    private String phone;

    private String addressName;

    private String formattedAddress;

    private Boolean isDefaultAddress;

    private Boolean isBillingAddress;

    private String label;
}
