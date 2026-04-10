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

    @ManyToOne
    private Order order;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User user;

    private LocalDateTime bookedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne
    private Ticket ticket;
}