package com.brokerage.controller;

import com.brokerage.model.Order;
import com.brokerage.model.User;
import com.brokerage.service.OrderService;
import com.brokerage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    // Admin kullanıcılar için bekleyen emirleri eşleştirme işlemi
    @PostMapping("/match-orders")
    public void matchPendingOrders() {
        orderService.matchPendingOrders();
    }

    // Tüm emirleri listeleme (Admin için)
    @GetMapping("/orders")
    public List<Order> listAllOrders() {
        return orderService.getAllOrders();
    }

    // Tüm kullanıcıları listeleme (Admin için)
    @GetMapping("/users")
    public List<User> listAllUsers() {
        return userService.getAllUsers();
    }

    // Bir kullanıcının emirlerini yönetme (Admin için)
    @GetMapping("/users/{userId}/orders")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByCustomerId(userId);
    }

    // Kullanıcıyı silme (isteğe bağlı)
    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
