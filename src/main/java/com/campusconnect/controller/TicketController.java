package com.campusconnect.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.model.Event;
import com.campusconnect.model.Order;
import com.campusconnect.model.Ticket;
import com.campusconnect.model.User;
import com.campusconnect.repository.EventRepository;
import com.campusconnect.repository.OrderRepository;
import com.campusconnect.repository.TicketRepository;
import com.campusconnect.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket req) {

        if (req.getUser() == null || req.getUser().getId() == null) {
            throw new RuntimeException("User id is required");
        }

        if (req.getEvent() == null || req.getEvent().getId() == null) {
            throw new RuntimeException("Event id is required");
        }

        if (req.getOrder() == null || req.getOrder().getId() == null) {
            throw new RuntimeException("Order id is required");
        }

        User user = userRepository.findById(req.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepository.findById(req.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Order order = orderRepository.findById(req.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Ticket ticket = new Ticket();
        ticket.setTicketCode(req.getTicketCode());
        ticket.setQrCode(req.getQrCode());
        ticket.setUser(user);
        ticket.setEvent(event);
        ticket.setOrder(order);
        ticket.setBookedAt(LocalDateTime.now());

        Ticket savedTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(savedTicket);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));

        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/code/{ticketCode}")
    public ResponseEntity<Ticket> getTicketByCode(@PathVariable String ticketCode) {
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new RuntimeException("Ticket not found with code " + ticketCode));

        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket req) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));

        User user = userRepository.findById(req.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepository.findById(req.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Order order = orderRepository.findById(req.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        ticket.setTicketCode(req.getTicketCode());
        ticket.setQrCode(req.getQrCode());
        ticket.setUser(user);
        ticket.setEvent(event);
        ticket.setOrder(order);

        Ticket updatedTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(updatedTicket);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ticket> patchTicket(@PathVariable Long id, @RequestBody Ticket req) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));

        if (req.getTicketCode() != null) {
            ticket.setTicketCode(req.getTicketCode());
        }

        if (req.getQrCode() != null) {
            ticket.setQrCode(req.getQrCode());
        }

        if (req.getUser() != null && req.getUser().getId() != null) {
            User user = userRepository.findById(req.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            ticket.setUser(user);
        }

        if (req.getEvent() != null && req.getEvent().getId() != null) {
            Event event = eventRepository.findById(req.getEvent().getId())
                    .orElseThrow(() -> new RuntimeException("Event not found"));
            ticket.setEvent(event);
        }

        if (req.getOrder() != null && req.getOrder().getId() != null) {
            Order order = orderRepository.findById(req.getOrder().getId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            ticket.setOrder(order);
        }

        Ticket updatedTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));

        ticketRepository.delete(ticket);
        return ResponseEntity.ok("Ticket deleted successfully");
    }
}