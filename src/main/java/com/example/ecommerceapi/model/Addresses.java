package com.example.ecommerceapi.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "addresses")
public class Addresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String addressName;

    @Column(columnDefinition = "TEXT")
    private String formattedAddress;

    @Column(nullable = false)
    private boolean defaultAddress = false;

    @Column(nullable = false)
    private boolean billingAddress = false;

    private String label;
}
