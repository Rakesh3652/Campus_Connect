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

import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.model.Event;

import lombok.RequiredArgsConstructor;

import com.campusconnect.model.EventMedia;
import com.campusconnect.repository.EventMediaRepository;
import com.campusconnect.repository.EventRepository;

@RestController
@RequestMapping("/event-media")
@RequiredArgsConstructor
public class EventMediaController {

    private final EventMediaRepository eventMediaRepository;
    private final EventRepository eventRepository;

    
    @PostMapping
    public ResponseEntity<EventMedia> createEventMedia(@RequestBody EventMedia req) {

        Event event = eventRepository.findById(req.getEvent().getId())
        .orElseThrow(()-> new ResourceNotFoundException("Event not found "));

        EventMedia media = new EventMedia();
        media.setMediaUrl(req.getMediaUrl());
        media.setType(req.getType());
        media.setEvent(event);

        EventMedia savedMedia = eventMediaRepository.save(media);
        return ResponseEntity.ok(savedMedia);
    }


    @GetMapping
    public ResponseEntity<List<EventMedia>> getAllEventMedia() {
        List<EventMedia> eventMedia = eventMediaRepository.findAll();
        return ResponseEntity.ok(eventMedia);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EventMedia> getEventMediaById(@PathVariable Long id) {
        EventMedia eventMedia = eventMediaRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Event media not found with id " + id));
        return ResponseEntity.ok(eventMedia);
    }


    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<EventMedia>> getByEventId(@PathVariable Long eventId) {
       List<EventMedia> eventMedia = eventMediaRepository.findByEventId(eventId);
        return ResponseEntity.ok(eventMedia);
    }

        @PutMapping("/{id}")
    public ResponseEntity<EventMedia> updateEventMedia(@PathVariable Long id, @RequestBody EventMedia req) {
        EventMedia eventMedia = eventMediaRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Event media not found with id " + id));

        Event event = eventRepository.findById(req.getEvent().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        eventMedia.setMediaUrl(req.getMediaUrl());
        eventMedia.setType(req.getType());
        eventMedia.setEvent(event);

        EventMedia updatedEventMedia = eventMediaRepository.save(eventMedia);
        return ResponseEntity.ok(updatedEventMedia);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventMedia> patchEventMedia(@PathVariable Long id, @RequestBody EventMedia req) {
        EventMedia eventMedia = eventMediaRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Event media not found with id " + id));


        if (req.getMediaUrl() != null) {
            eventMedia.setMediaUrl(req.getMediaUrl());
        }

        if (req.getType() != null) {
            eventMedia.setType(req.getType());
        }

        if (req.getEvent() != null && req.getEvent().getId() != null) {
            Event event = eventRepository.findById(req.getEvent().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
            eventMedia.setEvent(event);
        }

        EventMedia updatedEventMedia = eventMediaRepository.save(eventMedia);
        return ResponseEntity.ok(updatedEventMedia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEventMedia(@PathVariable Long id) {
        EventMedia eventMedia = eventMediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event media not found with id " + id));

        eventMediaRepository.delete(eventMedia);
        return ResponseEntity.ok("Event media deleted successfully");
    }

}
