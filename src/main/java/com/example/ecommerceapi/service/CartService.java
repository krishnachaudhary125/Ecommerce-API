package com.example.ecommerceapi.service;

import com.example.ecommerceapi.dto.CartItemResponse;
import com.example.ecommerceapi.model.CartItem;
import com.example.ecommerceapi.model.Product;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.repository.CartRepository;
import com.example.ecommerceapi.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public void addToCart(User user, Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartRepository
                .findByUserAndProduct(user, product)
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(1)
                    .build();
        }

        cartRepository.save(cartItem);
    }

    public List<CartItemResponse> getCart(User user) {

        return cartRepository.findAllByUser(user)
                .stream()
                .map(cartItem -> new CartItemResponse(
                        cartItem.getProduct().getId(),
                        cartItem.getProduct().getTitle(),
                        cartItem.getProduct().getThumbnail(),
                        cartItem.getProduct().getPrice(),
                        cartItem.getQuantity()
                ))
                .toList();
    }

    @Transactional
    public void removeFromCart(User user, Long productId) {

        CartItem cartItem = cartRepository
                .findByUserAndProductId(user, productId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartRepository.delete(cartItem);
    }

    @Transactional
    public void clearCart(User user) {
        cartRepository.deleteAllByUser(user);
    }

    public CartItemResponse toResponse(CartItem item) {
        return CartItemResponse.builder()
                .productId(item.getProduct().getId())
                .title(item.getProduct().getTitle())
                .thumbnail(item.getProduct().getThumbnail())
                .price(item.getProduct().getPrice())
                .quantity(item.getQuantity())
                .build();
    }

    @Transactional
    public CartItemResponse decreaseQuantity(User user, Long productId) {

        CartItem cartItem = cartRepository
                .findByUserAndProductId(user, productId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (cartItem.getQuantity() == 1) {
            cartRepository.delete(cartItem);
            return null;
        }

        cartItem.setQuantity(cartItem.getQuantity() - 1);

        cartRepository.save(cartItem);

        return toResponse(cartItem);
    }
}
