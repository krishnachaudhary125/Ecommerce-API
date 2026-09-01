package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.AddressRequest;
import com.example.ecommerceapi.dto.AddressResponse;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.service.AddressService;
import com.example.ecommerceapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    private final UserService userService;

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(
            @RequestBody AddressRequest request
    ) {
        User currentUser = userService.getCurrentUser();

        return ResponseEntity.ok(
                addressService.createAddress(
                        currentUser,
                        request
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAddress() {

        User currentUser = userService.getCurrentUser();

        return ResponseEntity.ok(
                addressService.getUserAddresses(currentUser)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddress(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(
                addressService.getAddress(id, user)
        );
    }
}
