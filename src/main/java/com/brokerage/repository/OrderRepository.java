package com.brokerage.repository;

import com.brokerage.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByStatus(String status);
    List<Order> findByCustomerIdAndCreateDateBetween(Long customerId, LocalDateTime startDate, LocalDateTime endDate);
}
