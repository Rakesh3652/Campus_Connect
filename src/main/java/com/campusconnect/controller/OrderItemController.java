package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.campusconnect.model.OrderItem;
import com.campusconnect.model.OrderStatus;
import com.campusconnect.repository.OrderItemRepository;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemRepository repository;

    // CREATE
    @PostMapping
    public ResponseEntity<OrderItem> create(@RequestBody OrderItem item) {
        return ResponseEntity.ok(repository.save(item));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<OrderItem>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                repository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Not found"))
        );
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> update(@PathVariable Long id,
                                            @RequestBody OrderItem details) {

        OrderItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        item.setQuantity(details.getQuantity());
        item.setPrice(details.getPrice());
        item.setStatus(details.getStatus());

        return ResponseEntity.ok(repository.save(item));
    }

    // PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<OrderItem> patch(@PathVariable Long id,
                                           @RequestBody OrderItem details) {

        OrderItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (details.getQuantity() != 0)
            item.setQuantity(details.getQuantity());

        if (details.getStatus() != null)
            item.setStatus(details.getStatus());

        return ResponseEntity.ok(repository.save(item));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    // 🔥 EXTRA GETs

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(repository.findByOrderId(orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderItem>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(repository.findByUserId(userId));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<OrderItem>> getByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(repository.findByEventId(eventId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderItem>> getByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(repository.findByStatus(status));
    }
}