package org.example.vedicvastram.service;

import org.example.vedicvastram.dto.CartDTO;
import org.example.vedicvastram.entity.Cart;
import org.example.vedicvastram.entity.CartItem;
import org.example.vedicvastram.respository.CartItemRepository;
import org.example.vedicvastram.respository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    public String addToCart(CartDTO dto, Long buyerId) {

        Cart cart = cartRepo.findByBuyerId(buyerId)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setBuyerId(buyerId);
                    return cartRepo.save(c);
                });

        Optional<CartItem> existing = cartItemRepo.findByCartIdAndProductId(cart.getId(), dto.getProductId());

        if (existing.isPresent()) {
            CartItem ci = existing.get();
            ci.setQuantity(ci.getQuantity() + dto.getQuantity());
            cartItemRepo.save(ci);
        } else {
            CartItem ci = new CartItem();
            ci.setCartId(cart.getId());
            ci.setProductId(dto.getProductId());
            ci.setQuantity(dto.getQuantity());
            cartItemRepo.save(ci);
        }

        return "Added to cart!";
    }

    public List<CartItem> getCart(Long buyerId) {
        Cart cart = cartRepo.findByBuyerId(buyerId)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setBuyerId(buyerId);
                    return cartRepo.save(c);
                });
        return cartItemRepo.findByCartId(cart.getId());
    }

    public String updateCart(Long itemId, Integer qty) {
        CartItem ci = cartItemRepo.findById(itemId).orElseThrow();
        ci.setQuantity(qty);
        cartItemRepo.save(ci);
        return "Updated!";
    }

    public String removeItem(Long id) {
        cartItemRepo.deleteById(id);
        return "Item removed!";
    }
}
