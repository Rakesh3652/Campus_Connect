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

import com.campusconnect.model.Payment;
import com.campusconnect.repository.PaymentRepository;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepository repository;

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody Payment payment) {
        return ResponseEntity.ok(repository.save(payment));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
            repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found"))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> update(@PathVariable Long id,
                                          @RequestBody Payment details) {

        Payment payment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        payment.setAmount(details.getAmount());
        payment.setStatus(details.getStatus());
        payment.setMethod(details.getMethod());

        return ResponseEntity.ok(repository.save(payment));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Payment> patch(@PathVariable Long id,
                                        @RequestBody Payment details) {

        Payment payment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (details.getStatus() != null)
            payment.setStatus(details.getStatus());

        return ResponseEntity.ok(repository.save(payment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    // 🔥 EXTRA
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Payment>> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(repository.findByOrderId(orderId));
    }
}