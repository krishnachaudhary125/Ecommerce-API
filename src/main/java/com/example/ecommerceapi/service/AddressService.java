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

        return addressRepository.findByUserId(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    private AddressResponse getAddress(Long id, User user) {

        Addresses addresses = addressRepository
                .findByIdAndUserId(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Address not found.")
                );

        return toResponse(addresses);
    }


    @Transactional
    private AddressResponse createAddress(
            User user,
            AddressRequest request
    ) {
        if (Boolean.TRUE.equals(request.getDefaultAddress())) {

            addressRepository
                    .findByUserIdAndIsDefault(user)
                    .ifPresent(address -> {
                        address.setDefaultAddress(false);
                        addressRepository.save(address);
                    });
        }

        Addresses addresses = Addresses.builder()
                .user(user)
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .addressName(request.getAddressName())
                .formattedAddress(request.getFormattedAddress())
                .defaultAddress(
                        Boolean.TRUE.equals(request.getDefaultAddress())
                )
                .billingAddress(
                        Boolean.TRUE.equals(request.getBillingAddress())
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
                address.getAddressName(),
                address.getFormattedAddress(),
                address.getDefaultAddress(),
                address.getBillingAddress(),
                address.getLabel()
        );
    }
}
