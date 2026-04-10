package com.campusconnect.repository;

import com.campusconnect.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CollegeRepository extends JpaRepository<College, Long> {

    List<College> findByCollegeName(String collegeName);

    List<College> findByNameContainingIgnoreCase(String collegeName);

    List<College> findByLocation(String location);

    // ✅ Search by location (both exact and partial match)
    List<College> findByLocationContainingIgnoreCase(String location);
}