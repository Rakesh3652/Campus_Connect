package com.campusconnect.controller;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campusconnect.domain.AccountStatus;
import com.campusconnect.model.Vendor;
import com.campusconnect.repository.VendorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorRepository vendorRepository;
    
    @PostMapping
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor){
        Vendor savedVendor= vendorRepository.save(vendor);
        return ResponseEntity.ok(savedVendor);
    }

    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors(){
        List<Vendor> vendors= vendorRepository.findAll();
        return ResponseEntity.ok(vendors);
    }

    @GetMapping("/{id}")    
    public ResponseEntity<Vendor> getVendorById(@PathVariable Long id){
        Vendor vendor= vendorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("vendor not found with id" + id));

        return ResponseEntity.ok(vendor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor vendorDetails){
        Vendor vendor= vendorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("vendor not found with id" + id));

        vendor.setName(vendorDetails.getName());
        vendor.setEmail(vendorDetails.getEmail());
        vendor.setPhoneNumber(vendorDetails.getPhoneNumber());
        vendor.setAccountStatus(vendorDetails.getAccountStatus());
        vendor.setCommittee(vendorDetails.getCommittee());

        return ResponseEntity.ok(vendorRepository.save(vendor));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Vendor> updateVendorPartial( @PathVariable Long id, @RequestBody Vendor vendorDetails){
            Vendor vendor= vendorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("vendor not found with id" + id));

    if (vendorDetails.getName() != null) {
        vendor.setName(vendorDetails.getName());
    }

    if (vendorDetails.getEmail() != null) {
        vendor.setEmail(vendorDetails.getEmail());
    }

    if (vendorDetails.getPhoneNumber() != null) {
        vendor.setPhoneNumber(vendorDetails.getPhoneNumber());
    }

    if (vendorDetails.getAccountStatus() != null) {
        vendor.setAccountStatus(vendorDetails.getAccountStatus());
    }

    if (vendorDetails.getCommittee() != null) {
        vendor.setCommittee(vendorDetails.getCommittee());
    }

    if (vendorDetails.getBusinessDetail() != null) {
        vendor.setBusinessDetail(vendorDetails.getBusinessDetail());
    }

    if (vendorDetails.getBankDetails() != null) {
        vendor.setBankDetails(vendorDetails.getBankDetails());
    }

    return ResponseEntity.ok(vendorRepository.save(vendor));
    }
    

        // ✅ DELETE EVENT
   @DeleteMapping("/{id}")
public ResponseEntity<String> deleteUser(@PathVariable Long id) {
 
    vendorRepository.findById(id).orElseThrow(() -> new RuntimeException("Vendor not found with id: " + id));
    vendorRepository.deleteById(id);
    return ResponseEntity.ok("Vendor deleted successfully");
}

  @GetMapping("/email/{email}")
public ResponseEntity<Vendor> getVendorByEmail(@PathVariable String email) {

    Vendor vendor = vendorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Vendor not found with email: " + email));

    return ResponseEntity.ok(vendor);
}


@GetMapping("/status/{status}")
    public ResponseEntity<List<Vendor>> getVendorsByStatus(@PathVariable AccountStatus status) {

        return ResponseEntity.ok(vendorRepository.findByAccountStatus(status));
    }

}
