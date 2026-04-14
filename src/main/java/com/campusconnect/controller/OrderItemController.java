package com.campusconnect.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.exception.BadRequestException;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.model.Event;
import com.campusconnect.model.Order;
import com.campusconnect.model.OrderItem;
import com.campusconnect.model.OrderStatus;
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

        if (req.getOrder() == null || req.getOrder().getId() == null) {
            throw new BadRequestException("Order id is required");
        }

        if (req.getUser() == null || req.getUser().getId() == null) {
            throw new BadRequestException("User id is required");
        }

        if (req.getEvent() == null || req.getEvent().getId() == null) {
            throw new BadRequestException("Event id is required");
        }

        if (req.getQuantity() <= 0) {
            throw new BadRequestException("Valid quantity is required");
        }

        if (req.getPrice() == null || req.getPrice() <= 0) {
            throw new BadRequestException("Valid price is required");
        }

        if (req.getStatus() == null) {
            throw new BadRequestException("Order status is required");
        }

        Order order = orderRepository.findById(req.getOrder().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        User user = userRepository.findById(req.getUser().getId())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Event event = eventRepository.findById(req.getEvent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        if (event.getIsActive() == null || !event.getIsActive()) {
            throw new BadRequestException("Event is not active");
        }

        if (event.getCapacity() == null || event.getBookedCount() == null) {
            throw new BadRequestException("Event capacity details are missing");
        }

        if (event.getBookedCount() + req.getQuantity() > event.getCapacity()) {
            throw new BadRequestException("Not enough seats available for this event");
        }

        Ticket ticket = null;
        if (req.getTicket() != null && req.getTicket().getId() != null) {
            ticket = ticketRepository.findById(req.getTicket().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
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

        event.setBookedCount(event.getBookedCount() + req.getQuantity());
        eventRepository.save(event);

        return ResponseEntity.ok(savedOrderItem);
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with id " + id));

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
                orderItemRepository.findByStatus(OrderStatus.valueOf(status.toUpperCase())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem req) {

        if (req.getOrder() == null || req.getOrder().getId() == null) {
            throw new BadRequestException("Order id is required");
        }

        if (req.getUser() == null || req.getUser().getId() == null) {
            throw new BadRequestException("User id is required");
        }

        if (req.getEvent() == null || req.getEvent().getId() == null) {
            throw new BadRequestException("Event id is required");
        }

        if (req.getQuantity() <= 0) {
            throw new BadRequestException("Valid quantity is required");
        }

        if (req.getPrice() == null || req.getPrice() <= 0) {
            throw new BadRequestException("Valid price is required");
        }

        if (req.getStatus() == null) {
            throw new BadRequestException("Order status is required");
        }

        OrderItem orderItem = orderItemRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with id " + id));

        Order order = orderRepository.findById(req.getOrder().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        User user = userRepository.findById(req.getUser().getId())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Event event = eventRepository.findById(req.getEvent().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        Ticket ticket = null;
        if (req.getTicket() != null && req.getTicket().getId() != null) {
            ticket = ticketRepository.findById(req.getTicket().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        }

        Event oldEvent = orderItem.getEvent();

        if (oldEvent.getId().equals(event.getId())) {
            int adjustedBookedCount = oldEvent.getBookedCount() - orderItem.getQuantity();

            if (adjustedBookedCount + req.getQuantity() > oldEvent.getCapacity()) {
                throw new BadRequestException("Not enough seats available for this event");
            }

            oldEvent.setBookedCount(adjustedBookedCount + req.getQuantity());
            eventRepository.save(oldEvent);
        } else {
            oldEvent.setBookedCount(oldEvent.getBookedCount() - orderItem.getQuantity());
            eventRepository.save(oldEvent);

            if (event.getIsActive() == null || !event.getIsActive()) {
                throw new BadRequestException("Event is not active");
            }

            if (event.getCapacity() == null || event.getBookedCount() == null) {
                throw new BadRequestException("Event capacity details are missing");
            }

            if (event.getBookedCount() + req.getQuantity() > event.getCapacity()) {
                throw new BadRequestException("Not enough seats available for this event");
            }

            event.setBookedCount(event.getBookedCount() + req.getQuantity());
            eventRepository.save(event);
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
        .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with id " + id));

        if (req.getQuantity() < 0) {
            throw new BadRequestException("Valid quantity is required");
        }

        if (req.getQuantity() > 0) {
            Event currentEvent = orderItem.getEvent();

            if (currentEvent.getIsActive() == null || !currentEvent.getIsActive()) {
                throw new BadRequestException("Event is not active");
            }

            if (currentEvent.getCapacity() == null || currentEvent.getBookedCount() == null) {
                throw new BadRequestException("Event capacity details are missing");
            }

            int adjustedBookedCount = currentEvent.getBookedCount() - orderItem.getQuantity();

            if (adjustedBookedCount + req.getQuantity() > currentEvent.getCapacity()) {
                throw new BadRequestException("Not enough seats available for this event");
            }

            currentEvent.setBookedCount(adjustedBookedCount + req.getQuantity());
            eventRepository.save(currentEvent);

            orderItem.setQuantity(req.getQuantity());
        }

        if (req.getPrice() != null) {
            if (req.getPrice() <= 0) {
                throw new BadRequestException("Valid price is required");
            }
            orderItem.setPrice(req.getPrice());
        }

        if (req.getStatus() != null) {
            orderItem.setStatus(req.getStatus());
        }

        if (req.getOrder() != null && req.getOrder().getId() != null) {
            Order order = orderRepository.findById(req.getOrder().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
            orderItem.setOrder(order);
        }

        if (req.getUser() != null && req.getUser().getId() != null) {
            User user = userRepository.findById(req.getUser().getId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            orderItem.setUser(user);
        }

        if (req.getEvent() != null && req.getEvent().getId() != null) {
            Event event = eventRepository.findById(req.getEvent().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
            orderItem.setEvent(event);
        }

        if (req.getTicket() != null && req.getTicket().getId() != null) {
            Ticket ticket = ticketRepository.findById(req.getTicket().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
            orderItem.setTicket(ticket);
        }

        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
        return ResponseEntity.ok(updatedOrderItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with id " + id));

        Event event = orderItem.getEvent();

        if (event != null && event.getBookedCount() != null) {
            int updatedBookedCount = event.getBookedCount() - orderItem.getQuantity();
            event.setBookedCount(Math.max(updatedBookedCount, 0));
            eventRepository.save(event);
        }

        orderItemRepository.delete(orderItem);
        return ResponseEntity.ok("OrderItem deleted successfully");
    }
}