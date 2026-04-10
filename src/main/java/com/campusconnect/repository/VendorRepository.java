package com.campusconnect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusconnect.model.Vendor;
import com.campusconnect.domain.AccountStatus;


public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findByEmail(String email);
    List<Vendor> findByAccountStatus(AccountStatus accountStatus);
}
