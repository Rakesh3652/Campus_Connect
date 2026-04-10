package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.model.Vendor;
import com.campusconnect.model.VendorReport;
import com.campusconnect.repository.VendorReportRepository;
import com.campusconnect.repository.VendorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vendor-reports")
@RequiredArgsConstructor
public class VendorReportController {

    private final VendorReportRepository vendorReportRepository;
    private final VendorRepository vendorRepository;

    @PostMapping
    public ResponseEntity<VendorReport> createVendorReport(@RequestBody VendorReport req) {

        if (req.getVendor() == null || req.getVendor().getId() == null) {
            throw new RuntimeException("Vendor id is required");
        }

        Vendor vendor = vendorRepository.findById(req.getVendor().getId())
        .orElseThrow(() -> new RuntimeException("Vendor not found"));

        VendorReport vendorReport = new VendorReport();
        vendorReport.setVendor(vendor);
        vendorReport.setTotalEarnings(req.getTotalEarnings());
        vendorReport.setTotalTicketSale(req.getTotalTicketSale());
        vendorReport.setTotalTax(req.getTotalTax());
        vendorReport.setCapacity(req.getCapacity());
        vendorReport.setNetEarnings(req.getNetEarnings());
        vendorReport.setTotalTransactions(req.getTotalTransactions());

        VendorReport savedVendorReport = vendorReportRepository.save(vendorReport);
        return ResponseEntity.ok(savedVendorReport);
    }

    @GetMapping
    public ResponseEntity<List<VendorReport>> getAllVendorReports() {
        return ResponseEntity.ok(vendorReportRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorReport> getVendorReportById(@PathVariable Long id) {
        VendorReport vendorReport = vendorReportRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("VendorReport not found with id " + id));

        return ResponseEntity.ok(vendorReport);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<VendorReport> getVendorReportByVendorId(@PathVariable Long vendorId) {
        VendorReport vendorReport = vendorReportRepository.findByVendorId(vendorId)
        .orElseThrow(() -> new RuntimeException("VendorReport not found for vendor id " + vendorId));

        return ResponseEntity.ok(vendorReport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorReport> updateVendorReport(@PathVariable Long id,
                                                           @RequestBody VendorReport req) {
        VendorReport vendorReport = vendorReportRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("VendorReport not found with id " + id));

        Vendor vendor = vendorRepository.findById(req.getVendor().getId())
        .orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendorReport.setVendor(vendor);
        vendorReport.setTotalEarnings(req.getTotalEarnings());
        vendorReport.setTotalTicketSale(req.getTotalTicketSale());
        vendorReport.setTotalTax(req.getTotalTax());
        vendorReport.setCapacity(req.getCapacity());
        vendorReport.setNetEarnings(req.getNetEarnings());
        vendorReport.setTotalTransactions(req.getTotalTransactions());

        VendorReport updatedVendorReport = vendorReportRepository.save(vendorReport);
        return ResponseEntity.ok(updatedVendorReport);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VendorReport> patchVendorReport(@PathVariable Long id,
                                                          @RequestBody VendorReport req) {
        VendorReport vendorReport = vendorReportRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("VendorReport not found with id " + id));

        if (req.getVendor() != null && req.getVendor().getId() != null) {
            Vendor vendor = vendorRepository.findById(req.getVendor().getId())
            .orElseThrow(() -> new RuntimeException("Vendor not found"));
            vendorReport.setVendor(vendor);
        }

        if (req.getTotalEarnings() != null) {
            vendorReport.setTotalEarnings(req.getTotalEarnings());
        }

        if (req.getTotalTicketSale() != null) {
            vendorReport.setTotalTicketSale(req.getTotalTicketSale());
        }

        if (req.getTotalTax() != null) {
            vendorReport.setTotalTax(req.getTotalTax());
        }

        if (req.getCapacity() != null) {
            vendorReport.setCapacity(req.getCapacity());
        }

        if (req.getNetEarnings() != null) {
            vendorReport.setNetEarnings(req.getNetEarnings());
        }

        if (req.getTotalTransactions() != null) {
            vendorReport.setTotalTransactions(req.getTotalTransactions());
        }

        VendorReport updatedVendorReport = vendorReportRepository.save(vendorReport);
        return ResponseEntity.ok(updatedVendorReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVendorReport(@PathVariable Long id) {
        VendorReport vendorReport = vendorReportRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("VendorReport not found with id " + id));

        vendorReportRepository.delete(vendorReport);
        return ResponseEntity.ok("VendorReport deleted successfully");
    }
}