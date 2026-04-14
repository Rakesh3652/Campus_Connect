package com.campusconnect.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
@AllArgsConstructor
@NoArgsConstructor

public class Review {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
     @Column(nullable=false)
    private String reviewText;

     @Column(nullable=false)
    private Double rating;

    @ElementCollection
    private List<String> eventImages;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User user;

    @Column(nullable=false)
    private LocalDateTime createdAt = LocalDateTime.now();

    
}
