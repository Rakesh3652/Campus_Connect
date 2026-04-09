package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.model.Event;
import com.campusconnect.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;

    // ✅ GET ALL EVENTS
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventRepository.findAll());
    }

    // ✅ GET EVENT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        return ResponseEntity.ok(event);
    }

    // ✅ CREATE EVENT
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {

        // default values
        event.setBookedCount(0);
        event.setIsActive(true);

        Event savedEvent = eventRepository.save(event);

        return ResponseEntity.ok(savedEvent);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id,
                                             @RequestBody Event eventDetails) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());
        event.setPrice(eventDetails.getPrice());
        event.setCapacity(eventDetails.getCapacity());
        event.setBookedCount(eventDetails.getBookedCount());
        event.setIsActive(eventDetails.getIsActive());
        event.setStartTime(eventDetails.getStartTime());
        event.setEndTime(eventDetails.getEndTime());
        event.setVendor(eventDetails.getVendor());
        event.setCategory(eventDetails.getCategory());
        event.setVenue(eventDetails.getVenue());

        return ResponseEntity.ok(eventRepository.save(event));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Event> updateEventPartial(@PathVariable Long id, @RequestBody Event eventDetails) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        if (eventDetails.getTitle() != null)
            event.setTitle(eventDetails.getTitle());

        if (eventDetails.getDescription() != null)
            event.setDescription(eventDetails.getDescription());

        if (eventDetails.getPrice() != null)
            event.setPrice(eventDetails.getPrice());

        if (eventDetails.getCapacity() != null)
            event.setCapacity(eventDetails.getCapacity());

        if (eventDetails.getBookedCount() != null)
            event.setBookedCount(eventDetails.getBookedCount());

        if (eventDetails.getIsActive() != null)
            event.setIsActive(eventDetails.getIsActive());

        if (eventDetails.getStartTime() != null)
            event.setStartTime(eventDetails.getStartTime());

        if (eventDetails.getEndTime() != null)
            event.setEndTime(eventDetails.getEndTime());

        if (eventDetails.getVendor() != null)
            event.setVendor(eventDetails.getVendor());

        if (eventDetails.getCategory() != null)
            event.setCategory(eventDetails.getCategory());

        if (eventDetails.getVenue() != null)
            event.setVenue(eventDetails.getVenue());

        return ResponseEntity.ok(eventRepository.save(event));
    }

    // ✅ DELETE EVENT
   @DeleteMapping("/{id}")
public ResponseEntity<String> deleteUser(@PathVariable Long id) {
 
    eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    eventRepository.deleteById(id);
    return ResponseEntity.ok("Event deleted successfully");
}


    // ✅ GET ACTIVE EVENTS
    @GetMapping("/active")
    public ResponseEntity<List<Event>> getActiveEvents() {
        return ResponseEntity.ok(eventRepository.findByIsActiveTrue());
    }

    // ✅ GET EVENTS BY CATEGORY
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Event>> getEventsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(eventRepository.findByCategoryId(categoryId));
    }

    // ✅ GET EVENTS BY VENDOR
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<Event>> getEventsByVendor(@PathVariable Long vendorId) {
        return ResponseEntity.ok(eventRepository.findByVendorId(vendorId));
    }
}