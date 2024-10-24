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

 
    @PostMapping("/match-orders")
    public void matchPendingOrders() {
        orderService.matchPendingOrders();
    }


    @GetMapping("/orders")
    public List<Order> listAllOrders() {
        return orderService.getAllOrders();
    }


    @GetMapping("/users")
    public List<User> listAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/users/{userId}/orders")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByCustomerId(userId);
    }


    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
