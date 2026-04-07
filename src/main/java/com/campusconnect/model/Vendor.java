package com.campusconnect.model;

import java.util.List;

import com.campusconnect.domain.AccountStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String mobile;

    @NotBlank
    @Column(unique = true)
    private String email;

    private String committee;

    @Embedded
    private BusinessDetail businessDetail = new BusinessDetail();

    @Embedded
    private BankDetails bankDetails = new BankDetails();

    private boolean isEmailVerified = false;

    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;

    @OneToMany(mappedBy = "vendor")
    private List<Event> events;

    @OneToOne
    private User user;
}