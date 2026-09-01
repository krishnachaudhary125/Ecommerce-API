package com.example.ecommerceapi.repository;

import com.example.ecommerceapi.model.Addresses;
import com.example.ecommerceapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Addresses, Long> {

    List<Addresses> findByUser(User user);

    Optional<Addresses> findByIdAndUser(Long id, User user);

    Optional<Addresses> findByUserAndIsDefaultAddress(
            User user,
            Boolean isDefaultAddress
    );
}