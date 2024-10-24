package com.brokerage.controller;

import com.brokerage.model.Order;
import com.brokerage.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(
            @RequestParam Long customerId,
            @RequestParam String assetName,
            @RequestParam String orderSide,
            @RequestParam Double size,
            @RequestParam Double price) {

        // Yeni bir emir oluşturma işlemi
        return orderService.createOrder(customerId, assetName, orderSide, size, price);
    }

    @GetMapping("/{customerId}")
    public List<Order> listOrders(
            @PathVariable Long customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        // Belirli bir müşteri kimliği ve tarih aralığına göre emirleri listeleme
        return orderService.getOrdersByCustomerIdAndDateRange(customerId, startDate, endDate);
    }

    @DeleteMapping("/{orderId}")
    public void cancelOrder(@PathVariable Long orderId) {
        // Bekleyen bir emri iptal etme işlemi
        orderService.cancelOrder(orderId);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        // Tüm emirleri listeleme
        return orderService.getAllOrders();
    }

    @PostMapping("/match")
    public void matchPendingOrders() {
        // Bekleyen emirleri eşleştirme işlemi
        orderService.matchPendingOrders();
    }
}
