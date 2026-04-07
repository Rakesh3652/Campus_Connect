package com.campusconnect.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusconnect.model.EventMedia;

public interface EventMediaRepository extends JpaRepository<EventMedia, Long> {

    List<EventMedia> findByEventName(String eventName); 
    List<EventMedia> findByEventId(Long eventId);
}
