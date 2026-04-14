package com.campusconnect.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.exception.BadRequestException;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.model.Payment;
import com.campusconnect.model.Transaction;
import com.campusconnect.repository.PaymentRepository;
import com.campusconnect.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final PaymentRepository paymentRepository;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction req) {

        if (req.getPayment() == null || req.getPayment().getId() == null) {
            throw new BadRequestException("Payment id is required");
        }

        if (req.getTransactionId() == null || req.getTransactionId().isBlank()) {
         throw new BadRequestException("Transaction id is required");
        }

        if (req.getGateway() == null || req.getGateway().isBlank()) {
         throw new BadRequestException("Gateway is required");
        }

        if (transactionRepository.findByTransactionId(req.getTransactionId()).isPresent()) {
         throw new BadRequestException("Transaction id already exists");
        }

        Payment payment = paymentRepository.findById(req.getPayment().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        Transaction transaction = new Transaction();
        transaction.setTransactionId(req.getTransactionId());
        transaction.setGateway(req.getGateway());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setPayment(payment);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + id));

        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/transaction-id/{transactionId}")
    public ResponseEntity<Transaction> getByTransactionId(@PathVariable String transactionId) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId)
        .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with transactionId " + transactionId));

        return ResponseEntity.ok(transaction);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id,
                                                     @RequestBody Transaction req) {

    if (req.getTransactionId() == null || req.getTransactionId().isBlank()) {
        throw new BadRequestException("Transaction id is required");
    }

    if (req.getGateway() == null || req.getGateway().isBlank()) {
        throw new BadRequestException("Gateway is required");
    }

    if (req.getPayment() == null || req.getPayment().getId() == null) {
        throw new BadRequestException("Payment id is required");
    }

    Transaction transaction = transactionRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + id));

    Transaction existingTransaction = transactionRepository.findByTransactionId(req.getTransactionId()).orElse(null);
    if (existingTransaction != null && !existingTransaction.getId().equals(id)) {
        throw new BadRequestException("Transaction id already exists");
    }

    Payment payment = paymentRepository.findById(req.getPayment().getId())
    .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

    transaction.setTransactionId(req.getTransactionId());
    transaction.setGateway(req.getGateway());
    transaction.setPayment(payment);

    Transaction updatedTransaction = transactionRepository.save(transaction);
    return ResponseEntity.ok(updatedTransaction);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Transaction> patchTransaction(@PathVariable Long id,
                                                    @RequestBody Transaction req) {
    Transaction transaction = transactionRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + id));

    if (req.getTransactionId() != null) {
        if (req.getTransactionId().isBlank()) {
            throw new BadRequestException("Transaction id cannot be empty");
        }

        Transaction existingTransaction = transactionRepository.findByTransactionId(req.getTransactionId()).orElse(null);
        if (existingTransaction != null && !existingTransaction.getId().equals(id)) {
            throw new BadRequestException("Transaction id already exists");
        }

        transaction.setTransactionId(req.getTransactionId());
    }

    if (req.getGateway() != null) {
        if (req.getGateway().isBlank()) {
            throw new BadRequestException("Gateway cannot be empty");
        }
        transaction.setGateway(req.getGateway());
    }

    if (req.getPayment() != null && req.getPayment().getId() != null) {
        Payment payment = paymentRepository.findById(req.getPayment().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        transaction.setPayment(payment);
    }

    Transaction updatedTransaction = transactionRepository.save(transaction);
    return ResponseEntity.ok(updatedTransaction);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        Transaction transaction = transactionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + id));

        transactionRepository.delete(transaction);
        return ResponseEntity.ok("Transaction deleted successfully");
    }
}