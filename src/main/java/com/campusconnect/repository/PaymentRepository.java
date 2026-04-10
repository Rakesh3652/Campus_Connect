package com.campusconnect.repository;

import com.campusconnect.model.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.campusconnect.domain.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByUserId(Long userId);
    List<Payment> findByOrderId(Long orderId);
    List<Payment> findByStatus(PaymentStatus status);
}