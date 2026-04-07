package com.campusconnect.repository;

import com.campusconnect.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByIsActiveTrue();
    
    List<Event> findByCategoryId(Long categoryId);

    List<Event> findByVendorId(Long vendorId);

}