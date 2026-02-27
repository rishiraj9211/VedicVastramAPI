package org.example.vedicvastram.controller;

import org.example.vedicvastram.dto.ProductDTO;
import org.example.vedicvastram.entity.Product;
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

    @GetMapping("/products")
    public List<Product> getMyProducts(@AuthenticationPrincipal CustomUserDetails user) {
        return service.getMyProducts(user.getUser().getId());
    }
}
