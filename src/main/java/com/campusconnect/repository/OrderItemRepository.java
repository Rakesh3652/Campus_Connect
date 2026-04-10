package com.campusconnect.repository;

import com.campusconnect.model.OrderItem;
import com.campusconnect.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);

    List<OrderItem> findByUserId(Long userId);

    List<OrderItem> findByEventId(Long eventId);

    List<OrderItem> findByStatus(OrderStatus status);
}