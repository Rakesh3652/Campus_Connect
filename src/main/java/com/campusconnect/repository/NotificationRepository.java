package com.campusconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusconnect.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
