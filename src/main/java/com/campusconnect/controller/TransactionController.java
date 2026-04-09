package com.campusconnect.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.campusconnect.model.Transaction;
import com.campusconnect.repository.TransactionRepository;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionRepository repository;

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody Transaction tx) {
        return ResponseEntity.ok(repository.save(tx));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> get(@PathVariable Long id) {
        return ResponseEntity.ok(
            repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found"))
        );
    }

    @GetMapping("/tx/{txId}")
    public ResponseEntity<Transaction> getByTxId(@PathVariable String txId) {
        return ResponseEntity.ok(
            repository.findByTransactionId(txId)
            .orElseThrow(() -> new RuntimeException("Not found"))
        );
    }
}
