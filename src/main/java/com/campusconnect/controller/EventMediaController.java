package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.campusconnect.model.EventMedia;
import com.campusconnect.repository.EventMediaRepository;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class EventMediaController {

    private final EventMediaRepository repository;

    // CREATE
    @PostMapping
    public ResponseEntity<EventMedia> create(@RequestBody EventMedia media) {
        return ResponseEntity.ok(repository.save(media));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<EventMedia>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EventMedia> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                repository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Media not found"))
        );
    }

    // UPDATE (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<EventMedia> update(@PathVariable Long id,
                                             @RequestBody EventMedia details) {

        EventMedia media = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        media.setEventName(details.getEventName());
        media.setMediaUrl(details.getMediaUrl());
        media.setType(details.getType());
        media.setEvent(details.getEvent());

        return ResponseEntity.ok(repository.save(media));
    }

    // PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<EventMedia> patch(@PathVariable Long id,
                                           @RequestBody EventMedia details) {

        EventMedia media = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        if (details.getMediaUrl() != null)
            media.setMediaUrl(details.getMediaUrl());

        if (details.getType() != null)
            media.setType(details.getType());

        return ResponseEntity.ok(repository.save(media));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    // 🔥 EXTRA GET

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<EventMedia>> getByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(repository.findByEventId(eventId));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<EventMedia>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(repository.findByEventName(name));
    }
}
