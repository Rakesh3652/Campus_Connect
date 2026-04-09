package com.campusconnect.controller;

import java.time.LocalDateTime;
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

import com.campusconnect.model.User;
import com.campusconnect.repository.UserRepository;
import com.campusconnect.response.SignupRequest;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserRepository userRepository;
    

@GetMapping("/{id}")
public ResponseEntity<User> getUserById(@PathVariable Long id) {

    return userRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

@GetMapping
public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userRepository.findAll());
}

@PutMapping("/{id}")
public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {

    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

    user.setFirstName(userDetails.getFirstName());
                user.setLastName(userDetails.getLastName());    
                user.setEmail(userDetails.getEmail());
                user.setPassword(userDetails.getPassword());
                user.setRole(userDetails.getRole());
                user.setPhoneNumber(userDetails.getPhoneNumber());
                // Update other fields as necessary
                return ResponseEntity.ok(userRepository.save(user));
    
    
}

@PatchMapping("/{id}")
public ResponseEntity<User> updateUserPartial(@PathVariable Long id,
                                              @RequestBody User userDetails) {

    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

    if (userDetails.getFirstName() != null) {
        user.setFirstName(userDetails.getFirstName());
    }

    if (userDetails.getLastName() != null) {
        user.setLastName(userDetails.getLastName());
    }

    if (userDetails.getEmail() != null) {
        user.setEmail(userDetails.getEmail());
    }

    if (userDetails.getPassword() != null) {
        user.setPassword(userDetails.getPassword());
    }

    if (userDetails.getRole() != null) {
        user.setRole(userDetails.getRole());
    }

    if (userDetails.getPhoneNumber() != null) {
        user.setPhoneNumber(userDetails.getPhoneNumber());
    }

    User updatedUser = userRepository.save(user);

    return ResponseEntity.ok(updatedUser);
}

@DeleteMapping("/{id}")
public ResponseEntity<String> deleteUser(@PathVariable Long id) {
 
    userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    userRepository.deleteById(id);

   
    return ResponseEntity.ok("User deleted successfully");
}

}   
