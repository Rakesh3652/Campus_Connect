package com.campusconnect.repository;

import com.campusconnect.model.VendorReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorReportRepository extends JpaRepository<VendorReport, Long> {

    // 🔥 Get report for a specific vendor
    Optional<VendorReport> findByVendorId(Long vendorId);

}