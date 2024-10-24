package com.brokerage.controller;

import com.brokerage.model.Order;
import com.brokerage.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/matchOrders")
    public void matchPendingOrders() {
        orderService.matchPendingOrders();
    }

    @GetMapping("/orders")
    public List<Order> listAllOrders() {
        return orderService.getAllOrders();
    }
}
