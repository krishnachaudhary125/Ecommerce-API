package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.CreateOrderRequest;
import com.example.ecommerceapi.dto.OrderResponse;
import com.example.ecommerceapi.model.Order;
import com.example.ecommerceapi.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}