package com.campusconnect.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusconnect.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
