package com.example.ecommerceapi.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@Table(
        name = "addresses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id", "user_id"})
        })
public class Addresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String addressName;

    @Column(columnDefinition = "TEXT")
    private String formattedAddress;

    @Column(nullable = false)
    private Boolean defaultAddress = false;

    @Column(nullable = false)
    private Boolean billingAddress = false;

    private String label;
}
