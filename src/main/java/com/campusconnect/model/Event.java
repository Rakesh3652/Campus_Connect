package com.campusconnect.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private Double price;

    private Integer capacity;
    private Integer bookedCount;

    private Boolean isActive = true;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne
    private Vendor vendor;

    @ManyToOne
    private EventCategory category;

    @ManyToOne
    private Venue venue;
}