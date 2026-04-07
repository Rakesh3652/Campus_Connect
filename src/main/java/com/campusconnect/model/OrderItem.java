package com.campusconnect.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private Double price;

    // ✅ Many items belong to one order
    @ManyToOne
    private Order order;

    // ✅ Each item is one event
    @ManyToOne
    private Event event;

    // ✅ Who booked it
    @ManyToOne
    private User user;

    // ✅ Booking time (correct place 👍)
    private LocalDateTime bookedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // ✅ Link to ticket (optional but good)
    @OneToOne
    private Ticket ticket;
}