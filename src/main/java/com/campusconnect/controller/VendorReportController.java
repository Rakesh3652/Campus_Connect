package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.exception.BadRequestException;
import com.campusconnect.exception.ResourceNotFoundException;
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
            throw new BadRequestException("Vendor id is required");
        }

        if (req.getTotalEarnings() != null && req.getTotalEarnings() < 0) {
            throw new BadRequestException("Total earnings cannot be negative");
        }

        if (req.getTotalTicketSale() != null && req.getTotalTicketSale() < 0) {
            throw new BadRequestException("Total ticket sale cannot be negative");
        }

        if (req.getTotalTax() != null && req.getTotalTax() < 0) {
            throw new BadRequestException("Total tax cannot be negative");
        }

        if (req.getCapacity() != null && req.getCapacity() < 0) {
            throw new BadRequestException("Capacity cannot be negative");
        }

        if (req.getTotalTransactions() != null && req.getTotalTransactions() < 0) {
            throw new BadRequestException("Total transactions cannot be negative");
        }

        Vendor vendor = vendorRepository.findById(req.getVendor().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        Long totalEarnings = req.getTotalEarnings() != null ? req.getTotalEarnings() : 0L;
        Long totalTax = req.getTotalTax() != null ? req.getTotalTax() : 0L;

        VendorReport vendorReport = new VendorReport();
        vendorReport.setVendor(vendor);
        vendorReport.setTotalEarnings(totalEarnings);
        vendorReport.setTotalTicketSale(req.getTotalTicketSale() != null ? req.getTotalTicketSale() : 0L);
        vendorReport.setTotalTax(totalTax);
        vendorReport.setCapacity(req.getCapacity());
        vendorReport.setNetEarnings(totalEarnings - totalTax);
        vendorReport.setTotalTransactions(req.getTotalTransactions() != null ? req.getTotalTransactions() : 0);

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
        .orElseThrow(() -> new ResourceNotFoundException("VendorReport not found with id " + id));

        return ResponseEntity.ok(vendorReport);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<VendorReport> getVendorReportByVendorId(@PathVariable Long vendorId) {
        VendorReport vendorReport = vendorReportRepository.findByVendorId(vendorId)
        .orElseThrow(() -> new ResourceNotFoundException("VendorReport not found for vendor id " + vendorId));

        return ResponseEntity.ok(vendorReport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorReport> updateVendorReport(@PathVariable Long id,
                                                           @RequestBody VendorReport req) {

        if (req.getVendor() == null || req.getVendor().getId() == null) {
            throw new BadRequestException("Vendor id is required");
        }

        if (req.getTotalEarnings() != null && req.getTotalEarnings() < 0) {
            throw new BadRequestException("Total earnings cannot be negative");
        }

        if (req.getTotalTicketSale() != null && req.getTotalTicketSale() < 0) {
            throw new BadRequestException("Total ticket sale cannot be negative");
        }

        if (req.getTotalTax() != null && req.getTotalTax() < 0) {
            throw new BadRequestException("Total tax cannot be negative");
        }

        if (req.getCapacity() != null && req.getCapacity() < 0) {
            throw new BadRequestException("Capacity cannot be negative");
        }

        if (req.getTotalTransactions() != null && req.getTotalTransactions() < 0) {
            throw new BadRequestException("Total transactions cannot be negative");
        }

        VendorReport vendorReport = vendorReportRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("VendorReport not found with id " + id));

        Vendor vendor = vendorRepository.findById(req.getVendor().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        Long totalEarnings = req.getTotalEarnings() != null ? req.getTotalEarnings() : 0L;
        Long totalTax = req.getTotalTax() != null ? req.getTotalTax() : 0L;

        vendorReport.setVendor(vendor);
        vendorReport.setTotalEarnings(totalEarnings);
        vendorReport.setTotalTicketSale(req.getTotalTicketSale() != null ? req.getTotalTicketSale() : 0L);
        vendorReport.setTotalTax(totalTax);
        vendorReport.setCapacity(req.getCapacity());
        vendorReport.setNetEarnings(totalEarnings - totalTax);
        vendorReport.setTotalTransactions(req.getTotalTransactions() != null ? req.getTotalTransactions() : 0);

        VendorReport updatedVendorReport = vendorReportRepository.save(vendorReport);
        return ResponseEntity.ok(updatedVendorReport);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VendorReport> patchVendorReport(@PathVariable Long id,
                                                          @RequestBody VendorReport req) {
        VendorReport vendorReport = vendorReportRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("VendorReport not found with id " + id));

        if (req.getVendor() != null && req.getVendor().getId() != null) {
            Vendor vendor = vendorRepository.findById(req.getVendor().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
            vendorReport.setVendor(vendor);
        }

        if (req.getTotalEarnings() != null) {
            if (req.getTotalEarnings() < 0) {
                throw new BadRequestException("Total earnings cannot be negative");
            }
            vendorReport.setTotalEarnings(req.getTotalEarnings());
        }

        if (req.getTotalTicketSale() != null) {
            if (req.getTotalTicketSale() < 0) {
                throw new BadRequestException("Total ticket sale cannot be negative");
            }
            vendorReport.setTotalTicketSale(req.getTotalTicketSale());
        }

        if (req.getTotalTax() != null) {
            if (req.getTotalTax() < 0) {
                throw new BadRequestException("Total tax cannot be negative");
            }
            vendorReport.setTotalTax(req.getTotalTax());
        }

        if (req.getCapacity() != null) {
            if (req.getCapacity() < 0) {
                throw new BadRequestException("Capacity cannot be negative");
            }
            vendorReport.setCapacity(req.getCapacity());
        }

        if (req.getTotalTransactions() != null) {
            if (req.getTotalTransactions() < 0) {
                throw new BadRequestException("Total transactions cannot be negative");
            }
            vendorReport.setTotalTransactions(req.getTotalTransactions());
        }

        Long totalEarnings = vendorReport.getTotalEarnings() != null ? vendorReport.getTotalEarnings() : 0L;
        Long totalTax = vendorReport.getTotalTax() != null ? vendorReport.getTotalTax() : 0L;
        vendorReport.setNetEarnings(totalEarnings - totalTax);

        VendorReport updatedVendorReport = vendorReportRepository.save(vendorReport);
        return ResponseEntity.ok(updatedVendorReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVendorReport(@PathVariable Long id) {
        VendorReport vendorReport = vendorReportRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("VendorReport not found with id " + id));

        vendorReportRepository.delete(vendorReport);
        return ResponseEntity.ok("VendorReport deleted successfully");
    }
}