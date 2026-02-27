package org.example.vedicvastram.controller;

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

    @GetMapping("/sellers/pending")
    public List<User> pendingSellers() {
        return service.getPendingSellers();
    }

    @PostMapping("/sellers/{id}/approve")
    public String approveSeller(@PathVariable Long id) {
        return service.approveSeller(id);
    }

    @GetMapping("/products/pending")
    public List<Product> pendingProducts() {
        return service.getPendingProducts();
    }

    @PostMapping("/products/{id}/approve")
    public String approveProduct(@PathVariable Long id) {
        return service.approveProduct(id);
    }
}
