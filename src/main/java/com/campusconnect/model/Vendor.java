package com.campusconnect.model;

import com.campusconnect.domain.AccountStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Vendor {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String vendorName;
    private String mobile;

    @NotBlank
    @Column(unique=true)
    private String email;
    private String password;

    @Embedded
    private BusinessDetail businessDetail = new BusinessDetail();

    @Embedded
    private BankDetails bankDetails = new BankDetails();

    private USER_ROLE role;
    private boolean isEmailVerified = false;

    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;
    
}
