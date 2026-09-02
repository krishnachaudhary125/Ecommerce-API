package com.example.ecommerceapi.dto;

import lombok.Data;

@Data
public class AddressRequest {

    private String fullName;

    private String phone;

    private String province;

    private String District;

    private String City;

    private String postalCode;

    private String addressName;

    private Boolean isDefaultAddress;

    private Boolean isBillingAddress;

    private String label;
}
