package com.campusconnect.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.exception.BadRequestException;
import com.campusconnect.exception.ResourceNotFoundException;
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

        if (req.getTicketCode() == null || req.getTicketCode().isBlank()) {
            throw new BadRequestException("Ticket code is required");
        }   

        if (ticketRepository.findByTicketCode(req.getTicketCode()).isPresent()) {
            throw new BadRequestException("Ticket code already exists");
        }

        if (req.getUser() == null || req.getUser().getId() == null) {
            throw new BadRequestException("User id is required");
        }

        if (req.getEvent() == null || req.getEvent().getId() == null) {
            throw new BadRequestException("Event id is required");
        }

        if (req.getOrder() == null || req.getOrder().getId() == null) {
            throw new BadRequestException("Order id is required");
        }

        User user = userRepository.findById(req.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Event event = eventRepository.findById(req.getEvent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        Order order = orderRepository.findById(req.getOrder().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

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
        .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + id));

        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/code/{ticketCode}")
    public ResponseEntity<Ticket> getTicketByCode(@PathVariable String ticketCode) {
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
        .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with code " + ticketCode));

        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket req) {

    if (req.getTicketCode() == null || req.getTicketCode().isBlank()) {
        throw new BadRequestException("Ticket code is required");
    }

    if (req.getUser() == null || req.getUser().getId() == null) {
        throw new BadRequestException("User id is required");
    }

    if (req.getEvent() == null || req.getEvent().getId() == null) {
        throw new BadRequestException("Event id is required");
    }

    if (req.getOrder() == null || req.getOrder().getId() == null) {
        throw new BadRequestException("Order id is required");
    }

        Ticket ticket = ticketRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + id));

        Ticket existingTicket = ticketRepository.findByTicketCode(req.getTicketCode()).orElse(null);
        if (existingTicket != null && !existingTicket.getId().equals(id)) {
         throw new BadRequestException("Ticket code already exists");
}

        User user = userRepository.findById(req.getUser().getId())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Event event = eventRepository.findById(req.getEvent().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        Order order = orderRepository.findById(req.getOrder().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

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
        .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + id));

        if (req.getTicketCode() != null) {
          if (req.getTicketCode().isBlank()) {
             throw new BadRequestException("Ticket code cannot be empty");
        }

        Ticket existingTicket = ticketRepository.findByTicketCode(req.getTicketCode()).orElse(null);
        if (existingTicket != null && !existingTicket.getId().equals(id)) {
            throw new BadRequestException("Ticket code already exists");
        }

        ticket.setTicketCode(req.getTicketCode());
        }

        if (req.getQrCode() != null) {
            ticket.setQrCode(req.getQrCode());
        }

        if (req.getUser() != null && req.getUser().getId() != null) {
            User user = userRepository.findById(req.getUser().getId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            ticket.setUser(user);
        }

        if (req.getEvent() != null && req.getEvent().getId() != null) {
            Event event = eventRepository.findById(req.getEvent().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
            ticket.setEvent(event);
        }

        if (req.getOrder() != null && req.getOrder().getId() != null) {
            Order order = orderRepository.findById(req.getOrder().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
            ticket.setOrder(order);
        }

        Ticket updatedTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
        Ticket ticket = ticketRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + id));

        ticketRepository.delete(ticket);
        return ResponseEntity.ok("Ticket deleted successfully");
    }
}