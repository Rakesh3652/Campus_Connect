package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        Vendor vendor = vendorRepository.findById(req.getVendor().getId())
        .orElseThrow(() -> new RuntimeException("Vendor not found"));


        EventCategory category = eventCategoryRepository.findById(req.getCategory().getId())
        .orElseThrow(() -> new RuntimeException("Category not found"));

        Venue venue = venueRepository.findById(req.getVenue().getId())
        .orElseThrow(() -> new RuntimeException("Venue not found"));
        
        Event event = new Event();
        event.setTitle(req.getTitle());
        event.setDescription(req.getDescription());
        event.setPrice(req.getPrice());
        event.setCapacity(req.getCapacity());
        event.setBookedCount(req.getBookedCount());
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
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

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

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        Vendor vendor = vendorRepository.findById(eventDetails.getVendor().getId())
        .orElseThrow(() -> new RuntimeException("Vendor not found"));

        EventCategory category = eventCategoryRepository.findById(eventDetails.getCategory().getId()). orElseThrow(() -> new RuntimeException("Category not found"));

        Venue venue = venueRepository.findById(eventDetails.getVenue().getId())
        .orElseThrow(() -> new RuntimeException("Venue not found"));

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

        Event updatedEvent = eventRepository.save(event);
        return ResponseEntity.ok(updatedEvent);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Event> patchEvent(@PathVariable Long id, @RequestBody Event eventDetails) {

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

        if (eventDetails.getVendor() != null  && eventDetails.getVendor().getId() != null){
            Vendor vendor = vendorRepository.findById(eventDetails.getVendor().getId())
            .orElseThrow(()  -> new RuntimeException ("Vendor not found"));
            event.setVendor(vendor);
        }
        
        if (eventDetails.getCategory()!= null  && eventDetails.getCategory().getId() != null){
            EventCategory category = eventCategoryRepository.findById(eventDetails.getCategory().getId())
            .orElseThrow(()  -> new RuntimeException ("Category not found"));
            event.setCategory(category);
        }

        if (eventDetails.getVenue() != null && eventDetails.getVenue().getId() != null){
            Venue venue = venueRepository.findById(eventDetails.getVenue().getId())
            .orElseThrow(()  -> new RuntimeException ("Venue not found"));
            event.setVenue(venue);
        }

        Event updatedEvent = eventRepository.save(event);
        return ResponseEntity.ok(updatedEvent);
    }


   @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
 
    Event event = eventRepository.findById(id)
    .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    eventRepository.deleteById(id);
    return ResponseEntity.ok("Event deleted successfully");
}

}