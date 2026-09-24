package com.example.ecommerceapi.service;

import com.example.ecommerceapi.dto.CreateOrderRequest;
import com.example.ecommerceapi.model.*;
import com.example.ecommerceapi.payment.EsewaPaymentService;
import com.example.ecommerceapi.repository.AddressRepository;
import com.example.ecommerceapi.repository.CartRepository;
import com.example.ecommerceapi.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;
    private final UserService userService;
    private final EsewaPaymentService esewaPaymentService;

    private static final double TAX_RATE = 0.13;
    private static final double SHIPPING_CHARGE = 70.00;

    @Transactional
    public Order createOrder(CreateOrderRequest request) {

        User user = userService.getCurrentUser();

        Addresses address = addressRepository
                .findByIdAndUser(request.getShippingAddressId(), user)
                .orElseThrow(() ->
                        new RuntimeException("Shipping address not found.")
                );

        List<CartItem> cartItems = cartRepository.findAllByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double subTotal = 0.0;

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            if (!Boolean.TRUE.equals(product.getActive())) {
                throw new RuntimeException("Product is no longer available: " + product.getTitle());
            }

            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for: " + product.getTitle());
            }

            double itemSubtotal =
                    product.getPrice() * cartItem.getQuantity();

            subTotal += itemSubtotal;

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .productTitle(product.getTitle())
                    .brand(product.getBrand())
                    .price(product.getPrice())
                    .quantity(cartItem.getQuantity())
                    .subtotal(itemSubtotal)
                    .build();

            orderItems.add(orderItem);
        }

        double tax = subTotal * TAX_RATE;

        double totalAmount = subTotal + tax + SHIPPING_CHARGE;

        String orderNumber =
                "ORD-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        Order order = Order.builder()
                .orderNumber(orderNumber)
                .user(user)

                .shippingFullName(address.getFullName())
                .shippingPhone(address.getPhone())
                .shippingProvince(address.getProvince())
                .shippingDistrict(address.getDistrict())
                .shippingPostalCode(address.getPostalCode())
                .shippingAddressName(address.getAddressName())
                .shippingLandmark(address.getLandmark())

                .subtotal(subTotal)
                .shippingCharge(SHIPPING_CHARGE)
                .tax(tax)
                .totalAmount(totalAmount)

                .paymentOption(request.getPaymentOption())
                .paymentStatus(PaymentStatus.PENDING)
                .orderStatus(OrderStatus.PENDING)

                .items(orderItems)
                .build();

        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }

        Order savedOrder = orderRepository.save(order);

        if (request.getPaymentOption() == PaymentOption.CASH_ON_DELIVERY) {
            cartRepository.deleteAllByUser(user);
        }

        return savedOrder;
    }

    @Transactional
    public List<Order> getUserOrders() {

        User user = userService.getCurrentUser();

        return orderRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    @Transactional
    public Order getOrderDetail(Long orderId) {

        User user = userService.getCurrentUser();

        return orderRepository
                .findByIdAndUser(orderId, user)
                .orElseThrow(() ->
                        new RuntimeException("Order not found.")
                );
    }

    @Transactional
    public Order verifyEsewaPayment(
            Long orderId,
            String refId
    ) {

        User user = userService.getCurrentUser();

        Order order = orderRepository
                .findByIdAndUser(orderId, user)
                .orElseThrow(() ->
                        new RuntimeException("Order not found.")
                );

        if (order.getPaymentOption() != PaymentOption.ESEWA) {
            throw new RuntimeException(
                    "This order is not an eSewa order."
            );
        }

        if (order.getPaymentStatus() == PaymentStatus.PAID) {
            return order;
        }

        boolean verified =
                esewaPaymentService.verifyPayment(
                        refId,
                        order.getTotalAmount()
                );

        if (!verified) {
            throw new RuntimeException(
                    "eSewa payment verification failed."
            );
        }

        order.setPaymentStatus(PaymentStatus.PAID);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        Order savedOrder = orderRepository.save(order);

        cartRepository.deleteAllByUser(user);

        return savedOrder;
    }

    @Transactional
    public void deletePendingEsewaOrder(Long orderId) {

        User user = userService.getCurrentUser();

        Order order = orderRepository
                .findByIdAndUser(orderId, user)
                .orElseThrow(() ->
                        new RuntimeException("Order not found.")
                );

        if (order.getPaymentOption() != PaymentOption.ESEWA) {
            throw new RuntimeException(
                    "Only eSewa orders can be deleted this way."
            );
        }

        if (order.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new RuntimeException(
                    "This payment can no longer be cancelled."
            );
        }

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new RuntimeException(
                    "This order can no longer be cancelled."
            );
        }

        orderRepository.delete(order);
    }
}
