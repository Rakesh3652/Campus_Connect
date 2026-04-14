package com.campusconnect.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.exception.BadRequestException;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.model.Notification;
import com.campusconnect.model.User;
import com.campusconnect.repository.NotificationRepository;
import com.campusconnect.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification req) {

        if (req.getUser() == null || req.getUser().getId() == null) {
            throw new BadRequestException("User id is required");
        }

        User user = userRepository.findById(req.getUser().getId())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (req.getMessage() == null || req.getMessage().isBlank()) {
            throw new BadRequestException("Notification message is required");
}

        Notification notification = new Notification();
        notification.setMessage(req.getMessage());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUser(user);

        Notification savedNotification = notificationRepository.save(notification);
        return ResponseEntity.ok(savedNotification);
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Notification notification = notificationRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id " + id));

        return ResponseEntity.ok(notification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id,
                                                           @RequestBody Notification req) {
        Notification notification = notificationRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id " + id));

        if (req.getMessage() != null) {
            notification.setMessage(req.getMessage());
        }

        if (req.getIsRead() != null) {
            notification.setIsRead(req.getIsRead());
        }

        if (req.getUser() != null && req.getUser().getId() != null) {
            User user = userRepository.findById(req.getUser().getId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            notification.setUser(user);
        }

        if (req.getMessage() == null || req.getMessage().isBlank()) {
         throw new BadRequestException("Notification message is required");
}
        Notification updatedNotification = notificationRepository.save(notification);
        return ResponseEntity.ok(updatedNotification);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Notification> patchNotification(@PathVariable Long id,
                                                          @RequestBody Notification req) {
        Notification notification = notificationRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id " + id));

        if (req.getMessage() != null) {
            notification.setMessage(req.getMessage());
        }

        if (req.getIsRead() != null) {
            notification.setIsRead(req.getIsRead());
        }

        if (req.getUser() != null && req.getUser().getId() != null) {
            User user = userRepository.findById(req.getUser().getId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            notification.setUser(user);
        }

        if (req.getMessage() == null || req.getMessage().isBlank()) {
             throw new BadRequestException("Notification message is required");
}

        Notification updatedNotification = notificationRepository.save(notification);
        return ResponseEntity.ok(updatedNotification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        Notification notification = notificationRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id " + id));

        notificationRepository.delete(notification);
        return ResponseEntity.ok("Notification deleted successfully");
    }
}