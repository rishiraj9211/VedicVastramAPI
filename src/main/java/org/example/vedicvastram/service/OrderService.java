package org.example.vedicvastram.service;

import org.example.vedicvastram.dto.OrderRequest;
import org.example.vedicvastram.entity.*;
import org.example.vedicvastram.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderItemRepository orderItemRepo;

    @Autowired
    private ProductRepository productRepo;

    public String checkout(Long buyerId, OrderRequest req) {

        Cart cart = cartRepo.findByBuyerId(buyerId).orElseThrow();

        List<CartItem> items = cartItemRepo.findByCartId(cart.getId());

        double total = 0.0;

        for (CartItem item : items) {
            Product p = productRepo.findById(item.getProductId()).orElseThrow();
            total += p.getPrice() * item.getQuantity();
        }

        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setAddressId(req.getAddressId());
        order.setPaymentType(req.getPaymentType());
        order.setStatus(OrderStatus.PLACED);
        order.setTotalAmount(total);
        order.setCreatedAt(LocalDateTime.now());
        orderRepo.save(order);

        items.forEach(ci -> {
            Product p = productRepo.findById(ci.getProductId()).orElseThrow();

            OrderItem oi = new OrderItem();
            oi.setOrderId(order.getId());
            oi.setProductId(p.getId());
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(p.getPrice());

            orderItemRepo.save(oi);
        });

        cartItemRepo.deleteAll(items);

        return "Order placed successfully!";
    }

    public List<Order> getOrderHistory(Long buyerId) {
        return orderRepo.findByBuyerId(buyerId);
    }
}
