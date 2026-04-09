package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.model.College;
import com.campusconnect.repository.CollegeRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/colleges")
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeRepository collegeRepository;

    // ✅ CREATE
    @PostMapping
    public ResponseEntity<College> createCollege(@RequestBody College college) {
        return ResponseEntity.ok(collegeRepository.save(college));
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<List<College>> getAllColleges() {
        return ResponseEntity.ok(collegeRepository.findAll());
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<College> getById(@PathVariable Long id) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found with id: " + id));
        return ResponseEntity.ok(college);
    }

    // ✅ FULL UPDATE (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<College> updateCollege(@PathVariable Long id,
                                                 @RequestBody College details) {

        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found"));

        college.setCollegeName(details.getCollegeName());
        college.setLocation(details.getLocation());

        return ResponseEntity.ok(collegeRepository.save(college));
    }

    // ✅ PARTIAL UPDATE (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<College> updatePartial(@PathVariable Long id,
                                                @RequestBody College details) {

        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found"));

        if (details.getCollegeName() != null)
            college.setCollegeName(details.getCollegeName());

        if (details.getLocation() != null)
            college.setLocation(details.getLocation());

        return ResponseEntity.ok(collegeRepository.save(college));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        collegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found"));

        collegeRepository.deleteById(id);
        return ResponseEntity.ok("College deleted successfully");
    }

    // 🔥 EXTRA GET APIs (FROM REPO)

    // Search by name
    @GetMapping("/search")
    public ResponseEntity<List<College>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(collegeRepository.findByNameContainingIgnoreCase(name));
    }

    // Exact location
    @GetMapping("/location")
    public ResponseEntity<List<College>> getByLocation(@RequestParam String location) {
        return ResponseEntity.ok(collegeRepository.findByLocation(location));
    }

    // Partial location search
    @GetMapping("/location/search")
    public ResponseEntity<List<College>> searchByLocation(@RequestParam String location) {
        return ResponseEntity.ok(collegeRepository.findByLocationContainingIgnoreCase(location));
    }
}