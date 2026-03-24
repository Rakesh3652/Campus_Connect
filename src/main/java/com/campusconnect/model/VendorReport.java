package com.campusconnect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendorReport {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Vendor vendor;


    // will there be ticket entity here? 
    private Long totalEarnings =0L;
    private Long totalTicketSale =0L;
    private Long totalTax =0L;
    private Long capacity;
    private Long netEarnings =0L;
    private int totalTransactions =0;

}
