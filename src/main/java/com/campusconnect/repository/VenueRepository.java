package com.campusconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusconnect.model.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    List<Venue> findByCollegeId(Long collegeId);
    
}
