package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.domain.AccountStatus;
import com.campusconnect.model.User;
import com.campusconnect.model.Vendor;
import com.campusconnect.repository.UserRepository;
import com.campusconnect.repository.VendorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor req) {

        User user = null;
        if (req.getUser() != null && req.getUser().getId() != null) {
            user = userRepository.findById(req.getUser().getId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        }

        Vendor vendor = new Vendor();
        vendor.setName(req.getName());
        vendor.setPhoneNumber(req.getPhoneNumber());
        vendor.setEmail(req.getEmail());
        vendor.setCommittee(req.getCommittee());
        vendor.setBusinessDetail(req.getBusinessDetail());
        vendor.setBankDetails(req.getBankDetails());
        vendor.setEmailVerified(req.isEmailVerified());
        vendor.setAccountStatus(req.getAccountStatus());
        vendor.setUser(user);

        Vendor savedVendor = vendorRepository.save(vendor);
        return ResponseEntity.ok(savedVendor);
    }

    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        return ResponseEntity.ok(vendorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable Long id) {
        Vendor vendor = vendorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Vendor not found with id " + id));

        return ResponseEntity.ok(vendor);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Vendor> getVendorByEmail(@PathVariable String email) {
        Vendor vendor = vendorRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Vendor not found with email " + email));

        return ResponseEntity.ok(vendor);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Vendor>> getVendorsByStatus(@PathVariable String status) {
        AccountStatus accountStatus = AccountStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(vendorRepository.findByAccountStatus(accountStatus));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor req) {
        Vendor vendor = vendorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Vendor not found with id " + id));

        User user = null;
        if (req.getUser() != null && req.getUser().getId() != null) {
            user = userRepository.findById(req.getUser().getId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        }

        vendor.setName(req.getName());
        vendor.setPhoneNumber(req.getPhoneNumber());
        vendor.setEmail(req.getEmail());
        vendor.setCommittee(req.getCommittee());
        vendor.setBusinessDetail(req.getBusinessDetail());
        vendor.setBankDetails(req.getBankDetails());
        vendor.setEmailVerified(req.isEmailVerified());
        vendor.setAccountStatus(req.getAccountStatus());
        vendor.setUser(user);

        Vendor updatedVendor = vendorRepository.save(vendor);
        return ResponseEntity.ok(updatedVendor);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Vendor> patchVendor(@PathVariable Long id, @RequestBody Vendor req) {
        Vendor vendor = vendorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Vendor not found with id " + id));

        if (req.getName() != null) {
            vendor.setName(req.getName());
        }

        if (req.getPhoneNumber() != null) {
            vendor.setPhoneNumber(req.getPhoneNumber());
        }

        if (req.getEmail() != null) {
            vendor.setEmail(req.getEmail());
        }

        if (req.getCommittee() != null) {
            vendor.setCommittee(req.getCommittee());
        }

        if (req.getBusinessDetail() != null) {
            vendor.setBusinessDetail(req.getBusinessDetail());
        }

        if (req.getBankDetails() != null) {
            vendor.setBankDetails(req.getBankDetails());
        }

        vendor.setEmailVerified(req.isEmailVerified());

        if (req.getAccountStatus() != null) {
            vendor.setAccountStatus(req.getAccountStatus());
        }

        if (req.getUser() != null && req.getUser().getId() != null) {
            User user = userRepository.findById(req.getUser().getId())
            .orElseThrow(() -> new RuntimeException("User not found"));
            vendor.setUser(user);
        }

        Vendor updatedVendor = vendorRepository.save(vendor);
        return ResponseEntity.ok(updatedVendor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long id) {
        Vendor vendor = vendorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Vendor not found with id " + id));

        vendorRepository.delete(vendor);
        return ResponseEntity.ok("Vendor deleted successfully");
    }
}