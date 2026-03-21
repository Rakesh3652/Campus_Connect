package com.campusconnect.model;

<<<<<<< HEAD
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity //doubt
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Transaction {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User student;

    @OneToMany
    private Order order;

    @ManyToOne
    private Vendor vendor;

    private LocalDateTime date = LocalDateTime.now();
=======
public class Transaction {
    
>>>>>>> 7c6335e2c3eee85c71b14ed54fe5b7a2f5bec643
}
