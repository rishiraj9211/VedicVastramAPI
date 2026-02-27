package org.example.vedicvastram.service;

import org.example.vedicvastram.entity.*;
import org.example.vedicvastram.respository.ProductRepository;
import org.example.vedicvastram.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    public List<User> getPendingSellers() {
        return userRepo.findAll()
                .stream()
                .filter(u -> u.getRole() == UserRole.SELLER && u.getStatus() == UserStatus.PENDING)
                .toList();
    }

    public String approveSeller(Long id) {
        User user = userRepo.findById(id).orElseThrow();
        user.setStatus(UserStatus.APPROVED);
        userRepo.save(user);
        return "Seller approved!";
    }

    public List<Product> getPendingProducts() {
        return productRepo.findByStatus(ProductStatus.PENDING);
    }

    public String approveProduct(Long id) {
        Product p = productRepo.findById(id).orElseThrow();
        p.setStatus(ProductStatus.APPROVED);
        productRepo.save(p);
        return "Product approved!";
    }
}