package com.campusconnect.repository;

import com.campusconnect.model.Home;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeRepository extends JpaRepository<Home, Long> {

    // 🔥 Get all events for a category
    List<Home> findByCategoryId(Long categoryId);

    // 🔥 Get all entries for a specific event
    List<Home> findByEventId(Long eventId);

}