package org.example.vedicvastram.controller;

import org.example.vedicvastram.dto.CartDTO;
import org.example.vedicvastram.entity.CartItem;
import org.example.vedicvastram.service.CartService;
import org.example.vedicvastram.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyer/cart")
public class CartController {

    @Autowired
    private CartService service;

    @PostMapping("/add")
    public String add(@RequestBody CartDTO dto,
                      @AuthenticationPrincipal CustomUserDetails user) {
        return service.addToCart(dto, user.getUser().getId());
    }

    @GetMapping
    public List<CartItem> list(@AuthenticationPrincipal CustomUserDetails user) {
        return service.getCart(user.getUser().getId());
    }

    @PutMapping("/update")
    public String update(@RequestParam Long itemId,
                         @RequestParam Integer qty) {
        return service.updateCart(itemId, qty);
    }

    @DeleteMapping("/remove/{id}")
    public String remove(@PathVariable Long id) {
        return service.removeItem(id);
    }
}