package org.example.vedicvastram.service;

import org.example.vedicvastram.dto.AdminDashboardResponse;
import org.example.vedicvastram.dto.AlertDTO;
import org.example.vedicvastram.dto.LabelValueTrend;
import org.example.vedicvastram.dto.SalesSeries;
import org.example.vedicvastram.dto.TopProductDTO;
import org.example.vedicvastram.dto.TopSellerDTO;
import org.example.vedicvastram.dto.UpdateUserRequest;
import org.example.vedicvastram.entity.Order;
import org.example.vedicvastram.entity.OrderStatus;
import org.example.vedicvastram.entity.Product;
import org.example.vedicvastram.entity.ProductStatus;
import org.example.vedicvastram.entity.User;
import org.example.vedicvastram.entity.UserRole;
import org.example.vedicvastram.entity.UserStatus;
import org.example.vedicvastram.respository.OrderRepository;
import org.example.vedicvastram.respository.ProductRepository;
import org.example.vedicvastram.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private OrderRepository orderRepo;

    public List<User> getPendingSellers() {
        return userRepo.findAll()
                .stream()
                .filter(u -> u.getRole() == UserRole.SELLER && u.getStatus() == UserStatus.PENDING)
                .toList();
    }

    public List<User> getSellers() {
        return userRepo.findAll()
                .stream()
                .filter(u -> u.getRole() == UserRole.SELLER)
                .toList();
    }

    public String approveSeller(Long id) {
        User user = userRepo.findById(id).orElseThrow();
        user.setStatus(UserStatus.APPROVED);
        userRepo.save(user);
        return "Seller approved!";
    }

    public String rejectSeller(Long id) {
        User user = userRepo.findById(id).orElseThrow();
        user.setStatus(UserStatus.REJECTED);
        userRepo.save(user);
        return "Seller rejected!";
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public String updateUser(Long id, UpdateUserRequest request) {
        User user = userRepo.findById(id).orElseThrow();
        if (request.getName() != null) user.setName(request.getName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getRole() != null) user.setRole(request.getRole());
        if (request.getStatus() != null) user.setStatus(request.getStatus());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getCity() != null) user.setCity(request.getCity());
        userRepo.save(user);
        return "User updated.";
    }

    public List<Product> getPendingProducts() {
        return productRepo.findByStatus(ProductStatus.PENDING);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public String approveProduct(Long id) {
        Product p = productRepo.findById(id).orElseThrow();
        p.setStatus(ProductStatus.APPROVED);
        productRepo.save(p);
        return "Product approved!";
    }

    public AdminDashboardResponse getDashboard() {
        long totalUsers = userRepo.count();
        long activeUsers = userRepo.findAll()
                .stream()
                .filter(u -> u.getStatus() == UserStatus.APPROVED || u.getStatus() == UserStatus.ACTIVE)
                .count();
        long pendingProducts = productRepo.findByStatus(ProductStatus.PENDING).size();
        long ordersToday = orderRepo.count(); // simple placeholder

        List<LabelValueTrend> kpis = List.of(
                new LabelValueTrend("Users", String.valueOf(totalUsers), "+3%"),
                new LabelValueTrend("Active", String.valueOf(activeUsers), "+1%"),
                new LabelValueTrend("Pending Products", String.valueOf(pendingProducts), "-2%")
        );

        List<TopSellerDTO> topSellers = userRepo.findAll()
                .stream()
                .filter(u -> u.getRole() == UserRole.SELLER)
                .map(u -> new TopSellerDTO(u.getName(), "$1000", 10))
                .limit(5)
                .toList();

        List<TopProductDTO> topProducts = productRepo.findAll()
                .stream()
                .map(p -> new TopProductDTO(
                        p.getTitle(),
                        "$" + (p.getPrice() != null ? p.getPrice() : 0),
                        p.getQuantity() != null ? p.getQuantity() : 0))
                .limit(5)
                .toList();

        List<AlertDTO> alerts = List.of(
                new AlertDTO("warning", "Pending approvals", "Review now"),
                new AlertDTO("info", "System healthy", "View logs")
        );

        List<SalesSeries> orderTrend = List.of(
                new SalesSeries("Orders", List.of(
                        new org.example.vedicvastram.dto.NameValue("Mon", 5.0),
                        new org.example.vedicvastram.dto.NameValue("Tue", 8.0),
                        new org.example.vedicvastram.dto.NameValue("Wed", 6.0)
                ))
        );

        return new AdminDashboardResponse(
                totalUsers,
                activeUsers,
                pendingProducts,
                ordersToday,
                kpis,
                topSellers,
                topProducts,
                alerts,
                orderTrend
        );
    }
}
