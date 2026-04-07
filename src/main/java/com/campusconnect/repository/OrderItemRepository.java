package com.campusconnect.repository;

import com.campusconnect.model.OrderItem;
import com.campusconnect.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // 🔥 Get all items of an order
    List<OrderItem> findByOrderId(Long orderId);

    // 🔥 Get all bookings of a user
    List<OrderItem> findByUserId(Long userId);

    // 🔥 Get all bookings for a specific event
    List<OrderItem> findByEventId(Long eventId);

    // 🔥 Filter by status (CONFIRMED, CANCELLED, etc.)
    List<OrderItem> findByStatus(OrderStatus status);
}