package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.domain.PaymentStatus;
import com.campusconnect.exception.BadRequestException;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.model.Order;
import com.campusconnect.model.Payment;
import com.campusconnect.model.User;
import com.campusconnect.repository.OrderRepository;
import com.campusconnect.repository.PaymentRepository;
import com.campusconnect.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment req) {

        if (req.getAmount() == null || req.getAmount() <= 0) {
         throw new BadRequestException("Valid payment amount is required");
        }

        if (req.getStatus() == null) {
         throw new BadRequestException("Payment status is required");
        }

        if (req.getMethod() == null) {
         throw new BadRequestException("Payment method is required");
        }

        if (req.getOrder() == null || req.getOrder().getId() == null) {
         throw new BadRequestException("Order id is required");
        }

        if (req.getUser() == null || req.getUser().getId() == null) {
         throw new BadRequestException("User id is required");
        }

        Order order = orderRepository.findById(req.getOrder().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        User user = userRepository.findById(req.getUser().getId())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Payment payment = new Payment();
        payment.setAmount(req.getAmount());
        payment.setStatus(req.getStatus());
        payment.setMethod(req.getMethod());
        payment.setOrder(order);
        payment.setUser(user);

        Payment savedPayment = paymentRepository.save(payment);
        return ResponseEntity.ok(savedPayment);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + id));

        return ResponseEntity.ok(payment);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getPaymentsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentRepository.findByUserId(userId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Payment>> getPaymentsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentRepository.findByOrderId(orderId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(@PathVariable String status) {
        PaymentStatus paymentStatus = PaymentStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(paymentRepository.findByStatus(paymentStatus));
    }

    @PutMapping("/{id}")
public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment req) {

    if (req.getAmount() == null || req.getAmount() <= 0) {
        throw new BadRequestException("Valid payment amount is required");
    }

    if (req.getStatus() == null) {
        throw new BadRequestException("Payment status is required");
    }

    if (req.getMethod() == null) {
        throw new BadRequestException("Payment method is required");
    }

    if (req.getOrder() == null || req.getOrder().getId() == null) {
        throw new BadRequestException("Order id is required");
    }

    if (req.getUser() == null || req.getUser().getId() == null) {
        throw new BadRequestException("User id is required");
    }

    Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + id));

    Order order = orderRepository.findById(req.getOrder().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

    User user = userRepository.findById(req.getUser().getId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    payment.setAmount(req.getAmount());
    payment.setStatus(req.getStatus());
    payment.setMethod(req.getMethod());
    payment.setOrder(order);
    payment.setUser(user);

    Payment updatedPayment = paymentRepository.save(payment);
    return ResponseEntity.ok(updatedPayment);
}

    @PatchMapping("/{id}")
    public ResponseEntity<Payment> patchPayment(@PathVariable Long id, @RequestBody Payment req) {
        Payment payment = paymentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + id));

        if (req.getAmount() != null) {
         if (req.getAmount() <= 0) {
             throw new BadRequestException("Valid payment amount is required");
        }
     payment.setAmount(req.getAmount());
}

        if (req.getStatus() != null) {
            payment.setStatus(req.getStatus());
        }

        if (req.getMethod() != null) {
            payment.setMethod(req.getMethod());
        }

        if (req.getOrder() != null && req.getOrder().getId() != null) {
            Order order = orderRepository.findById(req.getOrder().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
            payment.setOrder(order);
        }

        if (req.getUser() != null && req.getUser().getId() != null) {
            User user = userRepository.findById(req.getUser().getId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            payment.setUser(user);
        }

        Payment updatedPayment = paymentRepository.save(payment);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        Payment payment = paymentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + id));

        paymentRepository.delete(payment);
        return ResponseEntity.ok("Payment deleted successfully");
    }
}