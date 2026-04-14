package com.campusconnect.controller;

import java.util.HashSet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campusconnect.exception.BadRequestException;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.model.Event;
import com.campusconnect.model.User;

import lombok.RequiredArgsConstructor;

import com.campusconnect.model.Wishlist;
import com.campusconnect.repository.EventRepository;
import com.campusconnect.repository.UserRepository;
import com.campusconnect.repository.WishlistRepository;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @PostMapping
    public ResponseEntity<Wishlist> createWishlist(@RequestBody Wishlist request) {

        if (request.getUser() == null || request.getUser().getId() == null) {
        throw new BadRequestException("User id is required");
          }

        User user = userRepository.findById(request.getUser().getId()).
        orElseThrow(()-> new ResourceNotFoundException("User not found"));

        if(wishlistRepository.findByUserId(user.getId()).isPresent()){
            throw new BadRequestException("Wishlist already exists for this user");
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setEvents(new HashSet<>());

        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        return ResponseEntity.ok(savedWishlist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wishlist> getWishlistById(@PathVariable Long id) {
            Wishlist wishlist = wishlistRepository.findById(id).
            orElseThrow(()-> new ResourceNotFoundException("Wishlist not found with id "+ id));

            return ResponseEntity.ok(wishlist);
    }

    @PutMapping("/{wishlistId}/add-event/{eventId}")
    public ResponseEntity<Wishlist> addEventToWishlist(@PathVariable Long wishlistId,
                                                       @PathVariable Long eventId ){
    
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
        .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));

        Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        if(wishlist.getEvents().contains(event)){
            throw new BadRequestException("Event already exists in wishlist");
        }

        wishlist.getEvents().add(event);
        Wishlist updatedWishlist = wishlistRepository.save(wishlist);
        return ResponseEntity.ok(updatedWishlist);
                }

    @PutMapping("/{wishlistId}/remove-event/{eventId}")
    public ResponseEntity<Wishlist> removeEventFromWishlist(@PathVariable Long wishlistId,
                                                       @PathVariable Long eventId ){
    
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
        .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));

        Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        if(!wishlist.getEvents().contains(event)){
            throw new BadRequestException("Event does not exist in wislist");
        }

        wishlist.getEvents().remove(event);
        Wishlist updatedWishlist = wishlistRepository.save(wishlist);
        return ResponseEntity.ok(updatedWishlist);
                }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWishlist(@PathVariable Long id) {
        Wishlist wishlist = wishlistRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Wishlist not found"));

        wishlistRepository.delete(wishlist);
        return ResponseEntity.ok("Wishlist deleted");
    }
}