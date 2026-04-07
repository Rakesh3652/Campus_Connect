package com.campusconnect.repository;

import com.campusconnect.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // ✅ Get payments by user
    List<Payment> findByUserId(Long userId);

    // ✅ Get payments by order
    List<Payment> findByOrderId(Long orderId);

    // ✅ Get payments by status
    List<Payment> findByStatus(String status);
}