package com.brokerage.service;

import com.brokerage.model.Order;
import com.brokerage.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Order order) {

        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByCustomerIdAndDateRange(Long customerId, LocalDateTime startDate, LocalDateTime endDate) {

        return orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        if (!"PENDING".equals(order.getStatus())) {
            throw new IllegalStateException("Only PENDING orders can be canceled.");
        }

        order.setStatus("CANCELED");
        orderRepository.save(order);
    }

    @Transactional
    public void matchPendingOrders() {

        List<Order> pendingOrders = orderRepository.findByStatus("PENDING");

        for (Order order : pendingOrders) {

            order.setStatus("MATCHED");
            orderRepository.save(order);
        }
    }
}
