package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.exception.BadRequestException;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.model.College;
import com.campusconnect.model.Venue;
import com.campusconnect.repository.CollegeRepository;
import com.campusconnect.repository.VenueRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueRepository venueRepository;
    private final CollegeRepository collegeRepository;

    @PostMapping
    public ResponseEntity<Venue> createVenue(@RequestBody Venue req) {

        if (req.getCollege() == null || req.getCollege().getId() == null) {
            throw new BadRequestException("College id is required");
        }

        College college = collegeRepository.findById(req.getCollege().getId())
        .orElseThrow(() -> new ResourceNotFoundException("College not found"));

        Venue venue = new Venue();
        venue.setName(req.getName());
        venue.setLocality(req.getLocality());
        venue.setAddress(req.getAddress());
        venue.setCity(req.getCity());
        venue.setState(req.getState());
        venue.setPincode(req.getPincode());
        venue.setMobile(req.getMobile());
        venue.setCollege(college);

        Venue savedVenue = venueRepository.save(venue);
        return ResponseEntity.ok(savedVenue);
    }

    @GetMapping
    public ResponseEntity<List<Venue>> getAllVenues() {
        return ResponseEntity.ok(venueRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
        Venue venue = venueRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + id));

        return ResponseEntity.ok(venue);
    }

    @GetMapping("/college/{collegeId}")
    public ResponseEntity<List<Venue>> getVenuesByCollegeId(@PathVariable Long collegeId) {
        return ResponseEntity.ok(venueRepository.findByCollegeId(collegeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venue> updateVenue(@PathVariable Long id, @RequestBody Venue req) {
        Venue venue = venueRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + id));

        College college = collegeRepository.findById(req.getCollege().getId())
        .orElseThrow(() -> new ResourceNotFoundException("College not found"));

        venue.setName(req.getName());
        venue.setLocality(req.getLocality());
        venue.setAddress(req.getAddress());
        venue.setCity(req.getCity());
        venue.setState(req.getState());
        venue.setPincode(req.getPincode());
        venue.setMobile(req.getMobile());
        venue.setCollege(college);

        Venue updatedVenue = venueRepository.save(venue);
        return ResponseEntity.ok(updatedVenue);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Venue> patchVenue(@PathVariable Long id, @RequestBody Venue req) {
        Venue venue = venueRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + id));

        if (req.getName() != null) {
            venue.setName(req.getName());
        }

        if (req.getLocality() != null) {
            venue.setLocality(req.getLocality());
        }

        if (req.getAddress() != null) {
            venue.setAddress(req.getAddress());
        }

        if (req.getCity() != null) {
            venue.setCity(req.getCity());
        }

        if (req.getState() != null) {
            venue.setState(req.getState());
        }

        if (req.getPincode() != null) {
            venue.setPincode(req.getPincode());
        }

        if (req.getMobile() != null) {
            venue.setMobile(req.getMobile());
        }

        if (req.getCollege() != null && req.getCollege().getId() != null) {
            College college = collegeRepository.findById(req.getCollege().getId())
            .orElseThrow(() -> new ResourceNotFoundException("College not found"));
            venue.setCollege(college);
        }

        Venue updatedVenue = venueRepository.save(venue);
        return ResponseEntity.ok(updatedVenue);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVenue(@PathVariable Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + id));

        venueRepository.delete(venue);
        return ResponseEntity.ok("Venue deleted successfully");
    }
}