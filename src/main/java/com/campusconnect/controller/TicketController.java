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

import com.campusconnect.model.Ticket;
import com.campusconnect.repository.TicketRepository;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketRepository repository;

    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody Ticket ticket) {
        return ResponseEntity.ok(repository.save(ticket));
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
            repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found"))
        );
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Ticket> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(
            repository.findByTicketCode(code)
            .orElseThrow(() -> new RuntimeException("Invalid ticket"))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> update(@PathVariable Long id,
                                         @RequestBody Ticket details) {

        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        ticket.setTicketCode(details.getTicketCode());
        ticket.setQrCode(details.getQrCode());

        return ResponseEntity.ok(repository.save(ticket));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ticket> patch(@PathVariable Long id,
                                       @RequestBody Ticket details) {

        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (details.getQrCode() != null)
            ticket.setQrCode(details.getQrCode());

        return ResponseEntity.ok(repository.save(ticket));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}