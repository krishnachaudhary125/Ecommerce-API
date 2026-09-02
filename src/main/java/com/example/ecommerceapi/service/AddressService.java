package com.example.ecommerceapi.service;

import com.example.ecommerceapi.dto.AddressRequest;
import com.example.ecommerceapi.dto.AddressResponse;
import com.example.ecommerceapi.model.Addresses;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public List<AddressResponse> getUserAddresses(User user) {

        return addressRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    public AddressResponse getAddress(Long id, User user) {

        Addresses addresses = addressRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Address not found.")
                );

        return toResponse(addresses);
    }


    @Transactional
    public AddressResponse createAddress(
            User user,
            AddressRequest request
    ) {
        if (Boolean.TRUE.equals(request.getIsDefaultAddress())) {

            addressRepository
                    .findByUserAndIsDefaultAddress(user, true)
                    .ifPresent(address -> {
                        address.setIsDefaultAddress(false);
                        addressRepository.save(address);
                    });
        }

        Addresses addresses = Addresses.builder()
                .user(user)
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .province(request.getProvince())
                .district(request.getDistrict())
                .city(request.getCity())
                .postalCode(request.getPostalCode())
                .addressName(request.getAddressName())
                .isDefaultAddress(
                        Boolean.TRUE.equals(request.getIsDefaultAddress())
                )
                .isBillingAddress(
                        Boolean.TRUE.equals(request.getIsBillingAddress())
                )
                .label(request.getLabel())
                .build();

        return toResponse(addressRepository.save(addresses));
    }


    private AddressResponse toResponse(Addresses address) {

        return new AddressResponse(
                address.getId(),
                address.getFullName(),
                address.getPhone(),
                address.getProvince(),
                address.getDistrict(),
                address.getCity(),
                address.getPostalCode(),
                address.getAddressName(),
                address.getIsDefaultAddress(),
                address.getIsBillingAddress(),
                address.getLabel()
        );
    }
}
