package com.campusconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusconnect.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {


}
