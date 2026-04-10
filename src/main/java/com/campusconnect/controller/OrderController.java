package com.campusconnect.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.model.Order;
import com.campusconnect.model.OrderItem;
import com.campusconnect.model.User;
import com.campusconnect.repository.OrderRepository;
import com.campusconnect.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order req) {

        if (req.getUser() == null || req.getUser().getId() == null) {
            throw new RuntimeException("User id is required");
        }

        User user = userRepository.findById(req.getUser().getId())
        .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setItems(req.getItems());

        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                item.setOrder(order);
            }
        }

        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Order not found with id " + id));

        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order req) {
        Order order = orderRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Order not found with id " + id));

        if (req.getUser() != null && req.getUser().getId() != null) {
            User user = userRepository.findById(req.getUser().getId())
            .orElseThrow(() -> new RuntimeException("User not found"));
            order.setUser(user);
        }

        if (req.getItems() != null) {
            order.getItems().clear();
            order.getItems().addAll(req.getItems());

            for (OrderItem item : order.getItems()) {
                item.setOrder(order);
            }
        }

        Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Order> patchOrder(@PathVariable Long id, @RequestBody Order req) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));

        if (req.getUser() != null && req.getUser().getId() != null) {
            User user = userRepository.findById(req.getUser().getId())
            .orElseThrow(() -> new RuntimeException("User not found"));
            order.setUser(user);
        }

        if (req.getItems() != null) {
            order.getItems().clear();
            order.getItems().addAll(req.getItems());

            for (OrderItem item : order.getItems()) {
                item.setOrder(order);
            }
        }

        Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Order not found with id " + id));

        orderRepository.delete(order);
        return ResponseEntity.ok("Order deleted successfully");
    }
}