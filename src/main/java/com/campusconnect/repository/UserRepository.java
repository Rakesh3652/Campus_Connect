package com.campusconnect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusconnect.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);
    Optional<User> findById(Long id);
    


}
