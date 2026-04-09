package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.model.Venue;
import com.campusconnect.repository.VenueRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueRepository repository;

    @PostMapping
    public ResponseEntity<Venue> create(@RequestBody Venue venue) {
        return ResponseEntity.ok(repository.save(venue));
    }

    @GetMapping
    public ResponseEntity<List<Venue>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venue> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
            repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Venue not found"))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venue> update(@PathVariable Long id,
                                        @RequestBody Venue details) {

        Venue venue = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        venue.setName(details.getName());
        venue.setCity(details.getCity());
        venue.setState(details.getState());
        venue.setAddress(details.getAddress());
        venue.setPincode(details.getPincode());

        return ResponseEntity.ok(repository.save(venue));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Venue> patch(@PathVariable Long id,
                                      @RequestBody Venue details) {

        Venue venue = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (details.getCity() != null)
            venue.setCity(details.getCity());

        if (details.getAddress() != null)
            venue.setAddress(details.getAddress());

        return ResponseEntity.ok(repository.save(venue));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}