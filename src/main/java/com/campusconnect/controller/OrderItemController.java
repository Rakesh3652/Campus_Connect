package com.campusconnect.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.model.Event;
import com.campusconnect.model.Order;
import com.campusconnect.model.OrderItem;
import com.campusconnect.model.Ticket;
import com.campusconnect.model.User;
import com.campusconnect.repository.EventRepository;
import com.campusconnect.repository.OrderItemRepository;
import com.campusconnect.repository.OrderRepository;
import com.campusconnect.repository.TicketRepository;
import com.campusconnect.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem req) {

        Order order = orderRepository.findById(req.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        User user = userRepository.findById(req.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepository.findById(req.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Ticket ticket = null;
        if (req.getTicket() != null && req.getTicket().getId() != null) {
            ticket = ticketRepository.findById(req.getTicket().getId())
                    .orElseThrow(() -> new RuntimeException("Ticket not found"));
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(req.getQuantity());
        orderItem.setPrice(req.getPrice());
        orderItem.setBookedAt(LocalDateTime.now());
        orderItem.setStatus(req.getStatus());
        orderItem.setOrder(order);
        orderItem.setUser(user);
        orderItem.setEvent(event);
        orderItem.setTicket(ticket);

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return ResponseEntity.ok(savedOrderItem);
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id " + id));

        return ResponseEntity.ok(orderItem);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderItemRepository.findByOrderId(orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderItemRepository.findByUserId(userId));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByEventId(@PathVariable Long eventId) {
        return ResponseEntity.ok(orderItemRepository.findByEventId(eventId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(
                orderItemRepository.findByStatus(
                        com.campusconnect.model.OrderStatus.valueOf(status.toUpperCase())
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem req) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id " + id));

        Order order = orderRepository.findById(req.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        User user = userRepository.findById(req.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepository.findById(req.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Ticket ticket = null;
        if (req.getTicket() != null && req.getTicket().getId() != null) {
            ticket = ticketRepository.findById(req.getTicket().getId())
                    .orElseThrow(() -> new RuntimeException("Ticket not found"));
        }

        orderItem.setQuantity(req.getQuantity());
        orderItem.setPrice(req.getPrice());
        orderItem.setStatus(req.getStatus());
        orderItem.setOrder(order);
        orderItem.setUser(user);
        orderItem.setEvent(event);
        orderItem.setTicket(ticket);

        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
        return ResponseEntity.ok(updatedOrderItem);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderItem> patchOrderItem(@PathVariable Long id, @RequestBody OrderItem req) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id " + id));

        if (req.getQuantity() != 0) {
            orderItem.setQuantity(req.getQuantity());
        }

        if (req.getPrice() != null) {
            orderItem.setPrice(req.getPrice());
        }

        if (req.getStatus() != null) {
            orderItem.setStatus(req.getStatus());
        }

        if (req.getOrder() != null && req.getOrder().getId() != null) {
            Order order = orderRepository.findById(req.getOrder().getId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            orderItem.setOrder(order);
        }

        if (req.getUser() != null && req.getUser().getId() != null) {
            User user = userRepository.findById(req.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            orderItem.setUser(user);
        }

        if (req.getEvent() != null && req.getEvent().getId() != null) {
            Event event = eventRepository.findById(req.getEvent().getId())
                    .orElseThrow(() -> new RuntimeException("Event not found"));
            orderItem.setEvent(event);
        }

        if (req.getTicket() != null && req.getTicket().getId() != null) {
            Ticket ticket = ticketRepository.findById(req.getTicket().getId())
                    .orElseThrow(() -> new RuntimeException("Ticket not found"));
            orderItem.setTicket(ticket);
        }

        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
        return ResponseEntity.ok(updatedOrderItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id " + id));

        orderItemRepository.delete(orderItem);
        return ResponseEntity.ok("OrderItem deleted successfully");
    }
}