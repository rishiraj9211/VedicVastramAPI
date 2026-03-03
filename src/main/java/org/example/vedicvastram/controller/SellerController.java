package org.example.vedicvastram.controller;

import org.example.vedicvastram.dto.LabelValueTrend;
import org.example.vedicvastram.dto.NameValue;
import org.example.vedicvastram.dto.ProductDTO;
import org.example.vedicvastram.dto.SellerAnalyticsResponse;
import org.example.vedicvastram.dto.SellerOrderDTO;
import org.example.vedicvastram.entity.Product;
import org.example.vedicvastram.entity.OrderStatus;
import org.example.vedicvastram.service.CustomUserDetails;
import org.example.vedicvastram.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService service;

    @PostMapping("/products")
    public String addProduct(@RequestBody ProductDTO dto,
                             @AuthenticationPrincipal CustomUserDetails user) {
        return service.addProduct(dto, user.getUser().getId());
    }

    @PutMapping("/products/{id}")
    public String updateProduct(@PathVariable Long id,
                                @RequestBody ProductDTO dto,
                                @AuthenticationPrincipal CustomUserDetails user) {
        return service.updateProduct(id, dto, user.getUser().getId());
    }

    @GetMapping("/products")
    public List<Product> getMyProducts(@AuthenticationPrincipal CustomUserDetails user) {
        return service.getMyProducts(user.getUser().getId());
    }

    @GetMapping("/orders")
    public List<SellerOrderDTO> getSellerOrders(@AuthenticationPrincipal CustomUserDetails user) {
        return service.getSellerOrders(user.getUser().getId());
    }

    @PatchMapping("/orders/{id}")
    public String updateOrderStatus(@PathVariable Long id,
                                    @RequestParam OrderStatus status,
                                    @AuthenticationPrincipal CustomUserDetails user) {
        return service.updateOrderStatus(id, status, user.getUser().getId());
    }

    @GetMapping("/dashboard/stats")
    public List<LabelValueTrend> stats(@AuthenticationPrincipal CustomUserDetails user) {
        return service.getDashboardStats(user.getUser().getId());
    }

    @GetMapping("/dashboard/sales")
    public List<NameValue> sales(@AuthenticationPrincipal CustomUserDetails user) {
        return service.getDashboardSales(user.getUser().getId());
    }

    @GetMapping("/dashboard/top-products")
    public List<NameValue> topProducts(@AuthenticationPrincipal CustomUserDetails user) {
        return service.getTopProducts(user.getUser().getId());
    }

    @GetMapping("/analytics")
    public SellerAnalyticsResponse analytics(@AuthenticationPrincipal CustomUserDetails user) {
        return service.getAnalytics(user.getUser().getId());
    }
}
