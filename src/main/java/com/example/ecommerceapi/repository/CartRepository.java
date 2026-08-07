package com.example.ecommerceapi.repository;


import com.example.ecommerceapi.model.CartItem;
import com.example.ecommerceapi.model.Product;
import com.example.ecommerceapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByUserAndProduct(User user, Product product);

    List<CartItem> findAllByUser(User user);

    void deleteAllByUser(User user);

    Optional<CartItem> findByUserAndProductId(User user, Long productId);
}
