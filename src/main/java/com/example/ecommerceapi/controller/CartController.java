package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.AddToCartRequest;
import com.example.ecommerceapi.dto.CartItemResponse;
import com.example.ecommerceapi.dto.UpdateCartRequest;
import com.example.ecommerceapi.model.CartItem;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.service.CartService;
import com.example.ecommerceapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CartItemResponse> addToCart(
            @RequestBody AddToCartRequest request) {

        User currentUser = userService.getCurrentUser();

        return ResponseEntity.ok(
                cartService.addToCart(
                        currentUser,
                        request.getProductId()
                )
        );
    }

    @PatchMapping("/product/{productId}/decrease")
    public ResponseEntity<CartItemResponse> decreaseQuantity(
            @PathVariable Long productId) {

        User currentUser = userService.getCurrentUser();

        CartItemResponse response =
                cartService.decreaseQuantity(currentUser, productId);

        if (response == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCart() {

        User currentUser = userService.getCurrentUser();

        return ResponseEntity.ok(
                cartService.getCart(currentUser)
        );
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @PathVariable Long productId) {

        User currentUser = userService.getCurrentUser();

        cartService.removeFromCart(currentUser, productId);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        User currentUser = userService.getCurrentUser();
        cartService.clearCart(currentUser);
        return ResponseEntity.noContent().build();
    }
}