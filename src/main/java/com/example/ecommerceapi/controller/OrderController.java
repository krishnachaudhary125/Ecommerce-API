package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.CreateOrderRequest;
import com.example.ecommerceapi.dto.OrderDetailResponse;
import com.example.ecommerceapi.dto.OrderResponse;
import com.example.ecommerceapi.model.Order;
import com.example.ecommerceapi.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request
    ) {

        Order order = orderService.createOrder(request);

        return ResponseEntity.ok(
                OrderResponse.from(order)
        );
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailResponse>> getUserOrders() {

        List<Order> orders = orderService.getUserOrders();

        List<OrderDetailResponse> response = orders
                .stream()
                .map(OrderDetailResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(
            @PathVariable Long orderId
    ) {

        Order order = orderService.getOrderDetail(orderId);

        return ResponseEntity.ok(
                OrderDetailResponse.from(order)
        );
    }
}