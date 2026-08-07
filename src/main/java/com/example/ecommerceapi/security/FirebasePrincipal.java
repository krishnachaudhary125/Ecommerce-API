package com.example.ecommerceapi.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FirebasePrincipal {

    private final String uid;
    private final String email;
}