package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.model.College;
import com.campusconnect.repository.CollegeRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/colleges")
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeRepository collegeRepository;

    @PostMapping
    public ResponseEntity<College> createCollege(@RequestBody College req) {
        College college = new College();
        college.setCollegeName(req.getCollegeName());
        college.setLocation(req.getLocation());
     
        College savedCollege = collegeRepository.save(college);
        return ResponseEntity.ok(savedCollege);
    }

    @GetMapping
    public ResponseEntity<List<College>> getAllColleges() {
        List<College> colleges = collegeRepository.findAll();
        return ResponseEntity.ok(colleges);
    }


    @GetMapping("/{id}")
    public ResponseEntity<College> getCollegeById(@PathVariable Long id) {
        College college = collegeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("College not found with id: " + id));
        return ResponseEntity.ok(college);
    }

    @GetMapping("/name/{collegeName}")
    public ResponseEntity<List<College>> getCollegeByName(@PathVariable String collegeName){
        List<College> colleges = collegeRepository.findByCollegeName(collegeName);
        return ResponseEntity.ok(colleges);
    }

    @GetMapping("/search/name/{collegeName}")
    public ResponseEntity<List<College>> searchCollegeByName(@PathVariable String collegeName){
        List<College> colleges = collegeRepository.findByCollegeNameContainingIgnoreCase(collegeName);
        return ResponseEntity.ok(colleges);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<College>> getCollegeByLocation(@PathVariable String location){
        List<College> colleges = collegeRepository.findByLocation(location);
        return ResponseEntity.ok(colleges);
    }

    @GetMapping("/search/location/{location}")
    public ResponseEntity<List<College>> searchCollegeByLocation(@PathVariable String location){
        List<College> colleges = collegeRepository.findByLocationContainingIgnoreCase(location);
        return ResponseEntity.ok(colleges);
    }


    @PutMapping("/{id}")
    public ResponseEntity<College> updateCollege(@PathVariable Long id,
                                                 @RequestBody College updateCollege) {

        College college = collegeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("College not found with id " +id));

        college.setCollegeName(updateCollege.getCollegeName());
        college.setLocation(updateCollege.getLocation());

        College savedCollege = collegeRepository.save(college);
        return ResponseEntity.ok(savedCollege);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<College> updatePartial(@PathVariable Long id,
                                                @RequestBody College details) {

        College college = collegeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("College not found with id " + id));

        if (details.getCollegeName() != null)
            college.setCollegeName(details.getCollegeName());

        if (details.getLocation() != null)
            college.setLocation(details.getLocation());

        
         College updatedCollege = collegeRepository.save(college);
         return ResponseEntity.ok(updatedCollege);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCollege(@PathVariable Long id) {
        College college = collegeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("College not found with id " + id));

        collegeRepository.delete(college);
        return ResponseEntity.ok("College deleted successfully");
    }
}