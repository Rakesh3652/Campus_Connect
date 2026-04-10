package com.campusconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusconnect.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    
}
