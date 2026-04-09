package com.campusconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.campusconnect.model.EventCategory;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Long> {
}