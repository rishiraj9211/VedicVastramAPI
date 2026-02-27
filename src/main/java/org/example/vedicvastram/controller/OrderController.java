package org.example.vedicvastram.controller;

import org.example.vedicvastram.dto.OrderRequest;
import org.example.vedicvastram.entity.Order;
import org.example.vedicvastram.service.CustomUserDetails;
import org.example.vedicvastram.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyer/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/checkout")
    public String checkout(@AuthenticationPrincipal CustomUserDetails user,
                           @RequestBody OrderRequest req) {
        return service.checkout(user.getUser().getId(), req);
    }

    @GetMapping("/history")
    public List<Order> history(@AuthenticationPrincipal CustomUserDetails user) {
        return service.getOrderHistory(user.getUser().getId());
    }
}
