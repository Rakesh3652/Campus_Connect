package com.campusconnect.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.model.Event;
import com.campusconnect.model.Review;
import com.campusconnect.model.User;
import com.campusconnect.repository.EventRepository;
import com.campusconnect.repository.ReviewRepository;
import com.campusconnect.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review req) {

        if (req.getEvent() == null || req.getEvent().getId() == null) {
            throw new RuntimeException("Event id is required");
        }

        if (req.getUser() == null || req.getUser().getId() == null) {
            throw new RuntimeException("User id is required");
        }

        Event event = eventRepository.findById(req.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        User user = userRepository.findById(req.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review();
        review.setReviewText(req.getReviewText());
        review.setRating(req.getRating());
        review.setEventImages(req.getEventImages());
        review.setEvent(event);
        review.setUser(user);
        review.setCreatedAt(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id " + id));

        return ResponseEntity.ok(review);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review req) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id " + id));

        Event event = eventRepository.findById(req.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        User user = userRepository.findById(req.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        review.setReviewText(req.getReviewText());
        review.setRating(req.getRating());
        review.setEventImages(req.getEventImages());
        review.setEvent(event);
        review.setUser(user);

        Review updatedReview = reviewRepository.save(review);
        return ResponseEntity.ok(updatedReview);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Review> patchReview(@PathVariable Long id, @RequestBody Review req) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id " + id));

        if (req.getReviewText() != null) {
            review.setReviewText(req.getReviewText());
        }

        if (req.getRating() != null) {
            review.setRating(req.getRating());
        }

        if (req.getEventImages() != null) {
            review.setEventImages(req.getEventImages());
        }

        if (req.getEvent() != null && req.getEvent().getId() != null) {
            Event event = eventRepository.findById(req.getEvent().getId())
                    .orElseThrow(() -> new RuntimeException("Event not found"));
            review.setEvent(event);
        }

        if (req.getUser() != null && req.getUser().getId() != null) {
            User user = userRepository.findById(req.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            review.setUser(user);
        }

        Review updatedReview = reviewRepository.save(review);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id " + id));

        reviewRepository.delete(review);
        return ResponseEntity.ok("Review deleted successfully");
    }
}