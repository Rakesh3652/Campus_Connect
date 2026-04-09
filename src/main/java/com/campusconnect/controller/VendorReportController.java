package com.campusconnect.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.campusconnect.model.VendorReport;
import com.campusconnect.repository.VendorReportRepository;

@RestController
@RequestMapping("/vendor-reports")
@RequiredArgsConstructor
public class VendorReportController {

    private final VendorReportRepository repository;

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<VendorReport> getByVendor(@PathVariable Long vendorId) {
        return ResponseEntity.ok(
            repository.findByVendorId(vendorId)
            .orElseThrow(() -> new RuntimeException("Not found"))
        );
    }
}