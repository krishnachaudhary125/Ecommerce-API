package com.example.ecommerceapi.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String addressName;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDefaultAddress = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isBillingAddress = false;

    private String label;

    private String landmark;
}
