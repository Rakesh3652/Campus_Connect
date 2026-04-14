package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.exception.BadRequestException;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.model.Event;
import com.campusconnect.model.EventCategory;
import com.campusconnect.model.Vendor;
import com.campusconnect.model.Venue;
import com.campusconnect.repository.EventCategoryRepository;
import com.campusconnect.repository.EventRepository;
import com.campusconnect.repository.VendorRepository;
import com.campusconnect.repository.VenueRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final VendorRepository vendorRepository;
    private final VenueRepository venueRepository;
    private final EventCategoryRepository eventCategoryRepository;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event req ) {

    if (req.getVendor() == null || req.getVendor().getId() == null) {
        throw new BadRequestException("Vendor id is required");
    }

    if (req.getCategory() == null || req.getCategory().getId() == null) {
        throw new BadRequestException("Category id is required");
    }

    if (req.getVenue() == null || req.getVenue().getId() == null) {
        throw new BadRequestException("Venue id is required");
    }

    if (req.getCapacity() == null || req.getCapacity() <= 0) {
        throw new BadRequestException("Valid capacity is required");
    }

        Vendor vendor = vendorRepository.findById(req.getVendor().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));


        EventCategory category = eventCategoryRepository.findById(req.getCategory().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Venue venue = venueRepository.findById(req.getVenue().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
        
        Event event = new Event();
        event.setTitle(req.getTitle());
        event.setDescription(req.getDescription());
        event.setPrice(req.getPrice());
        event.setCapacity(req.getCapacity());
        event.setBookedCount(0);
        event.setIsActive(true);
        event.setStartTime(req.getStartTime());
        event.setEndTime(req.getEndTime());
        event.setVenue(venue);
        event.setVendor(vendor);
        event.setCategory(category);

        Event savedEvent = eventRepository.save(event);
        return ResponseEntity.ok(savedEvent);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventRepository.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

        return ResponseEntity.ok(event);
    }


    @GetMapping("/active")
    public ResponseEntity<List<Event>> getActiveEvents() {
        return ResponseEntity.ok(eventRepository.findByIsActiveTrue());
    }

    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Event>> getEventsByCategory(@PathVariable Long categoryId) {
        List<Event> event = eventRepository.findByCategoryId(categoryId);
        return ResponseEntity.ok(event);
    }


    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<Event>> getEventsByVendor(@PathVariable Long vendorId) {
        List<Event> event = eventRepository.findByVendorId(vendorId);
        return ResponseEntity.ok(event);
    }


@PutMapping("/{id}")
public ResponseEntity<Event> updateEvent(@PathVariable Long id,
                                         @RequestBody Event eventDetails) {

    if (eventDetails.getVendor() == null || eventDetails.getVendor().getId() == null) {
        throw new BadRequestException("Vendor id is required");
    }

    if (eventDetails.getCategory() == null || eventDetails.getCategory().getId() == null) {
        throw new BadRequestException("Category id is required");
    }

    if (eventDetails.getVenue() == null || eventDetails.getVenue().getId() == null) {
        throw new BadRequestException("Venue id is required");
    }

    if (eventDetails.getCapacity() == null || eventDetails.getCapacity() <= 0) {
        throw new BadRequestException("Valid capacity is required");
    }

    if (eventDetails.getBookedCount() == null || eventDetails.getBookedCount() < 0) {
        throw new BadRequestException("Valid booked count is required");
    }

    if (eventDetails.getBookedCount() > eventDetails.getCapacity()) {
        throw new BadRequestException("Booked count cannot be greater than capacity");
    }

    Event event = eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

    Vendor vendor = vendorRepository.findById(eventDetails.getVendor().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

    EventCategory category = eventCategoryRepository.findById(eventDetails.getCategory().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

    Venue venue = venueRepository.findById(eventDetails.getVenue().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));

    event.setTitle(eventDetails.getTitle());
    event.setDescription(eventDetails.getDescription());
    event.setPrice(eventDetails.getPrice());
    event.setCapacity(eventDetails.getCapacity());
    event.setBookedCount(eventDetails.getBookedCount());
    event.setIsActive(eventDetails.getIsActive());
    event.setStartTime(eventDetails.getStartTime());
    event.setEndTime(eventDetails.getEndTime());
    event.setVendor(vendor);
    event.setCategory(category);
    event.setVenue(venue);

    Event updatedEvent = eventRepository.save(event);
    return ResponseEntity.ok(updatedEvent);
}

    @PatchMapping("/{id}")
    public ResponseEntity<Event> patchEvent(@PathVariable Long id, @RequestBody Event eventDetails) {

    Event event = eventRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

    if (eventDetails.getTitle() != null) {
        event.setTitle(eventDetails.getTitle());
    }

    if (eventDetails.getDescription() != null) {
        event.setDescription(eventDetails.getDescription());
    }

    if (eventDetails.getPrice() != null) {
        event.setPrice(eventDetails.getPrice());
    }

    if (eventDetails.getCapacity() != null) {
        if (eventDetails.getCapacity() <= 0) {
            throw new BadRequestException("Valid capacity is required");
        }
        if (event.getBookedCount() != null && eventDetails.getCapacity() < event.getBookedCount()) {
            throw new BadRequestException("Capacity cannot be less than booked count");
        }
        event.setCapacity(eventDetails.getCapacity());
    }

    if (eventDetails.getBookedCount() != null) {
        if (eventDetails.getBookedCount() < 0) {
            throw new BadRequestException("Valid booked count is required");
        }

        Integer finalCapacity = eventDetails.getCapacity() != null ? eventDetails.getCapacity() : event.getCapacity();
        if (finalCapacity != null && eventDetails.getBookedCount() > finalCapacity) {
            throw new BadRequestException("Booked count cannot be greater than capacity");
        }

        event.setBookedCount(eventDetails.getBookedCount());
    }

    if (eventDetails.getIsActive() != null) {
        event.setIsActive(eventDetails.getIsActive());
    }

    if (eventDetails.getStartTime() != null) {
        event.setStartTime(eventDetails.getStartTime());
    }

    if (eventDetails.getEndTime() != null) {
        event.setEndTime(eventDetails.getEndTime());
    }

    if (eventDetails.getVendor() != null && eventDetails.getVendor().getId() != null) {
        Vendor vendor = vendorRepository.findById(eventDetails.getVendor().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
        event.setVendor(vendor);
    }

    if (eventDetails.getCategory() != null && eventDetails.getCategory().getId() != null) {
        EventCategory category = eventCategoryRepository.findById(eventDetails.getCategory().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        event.setCategory(category);
    }

    if (eventDetails.getVenue() != null && eventDetails.getVenue().getId() != null) {
        Venue venue = venueRepository.findById(eventDetails.getVenue().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
        event.setVenue(venue);
    }

    Event updatedEvent = eventRepository.save(event);
    return ResponseEntity.ok(updatedEvent);
}

   @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
 
    Event event = eventRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
    eventRepository.deleteById(id);
    return ResponseEntity.ok("Event deleted successfully");
}

}