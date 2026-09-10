package com.example.ecommerceapi.repository;

import com.example.ecommerceapi.model.Order;
import com.example.ecommerceapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findAllByUserOrderByCreatedAtDesc(User user);
    boolean existsByOrderNumber(String orderNumber);
}
