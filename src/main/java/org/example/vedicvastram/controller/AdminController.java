package org.example.vedicvastram.controller;

import org.example.vedicvastram.dto.AdminDashboardResponse;
import org.example.vedicvastram.dto.UpdateUserRequest;
import org.example.vedicvastram.entity.Product;
import org.example.vedicvastram.entity.User;
import org.example.vedicvastram.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService service;

    @GetMapping("/sellers")
    public List<User> sellers() {
        return service.getSellers();
    }

    @GetMapping("/sellers/pending")
    public List<User> pendingSellers() {
        return service.getPendingSellers();
    }

    @PostMapping("/sellers/{id}/approve")
    public String approveSeller(@PathVariable Long id) {
        return service.approveSeller(id);
    }

    @PostMapping("/sellers/{id}/reject")
    public String rejectSeller(@PathVariable Long id) {
        return service.rejectSeller(id);
    }

    @GetMapping("/products/pending")
    public List<Product> pendingProducts() {
        return service.getPendingProducts();
    }

    @GetMapping("/products")
    public List<Product> allProducts() {
        return service.getAllProducts();
    }

    @PostMapping("/products/{id}/approve")
    public String approveProduct(@PathVariable Long id) {
        return service.approveProduct(id);
    }

    @GetMapping("/users")
    public List<User> users() {
        return service.getUsers();
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return service.updateUser(id, request);
    }

    @GetMapping("/dashboard")
    public AdminDashboardResponse dashboard() {
        return service.getDashboard();
    }
}
