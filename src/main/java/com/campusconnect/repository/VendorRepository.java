package com.campusconnect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusconnect.model.Vendor;
import com.campusconnect.domain.AccountStatus;


public interface VendorRepository extends JpaRepository<Vendor, Long> {

    List<Vendor> findByEmail(String email);
    Optional<Vendor> findByMobile(String mobile);
    List<Vendor> findByAccountStatus(AccountStatus accountStatus);
}
