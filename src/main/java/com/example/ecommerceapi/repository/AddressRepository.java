package com.example.ecommerceapi.repository;

import com.example.ecommerceapi.model.Addresses;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {

    List<Addresses> findByUserId(Long userId);

    Optional<Addresses> findByIdAndUserId(Long id, Long userId);

    Optional<Addresses> findByUserIdAndIsDefault(Long userId);
}
