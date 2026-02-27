package org.example.vedicvastram.controller;

import org.example.vedicvastram.entity.Product;
import org.example.vedicvastram.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> all() {
        return service.getAllApproved();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/filter")
    public List<Product> filter(
            @RequestParam(required=false) String brand,
            @RequestParam(required=false) String color,
            @RequestParam(required=false) String fabric,
            @RequestParam(required=false) String size
    ) {
        return service.filter(brand, color, fabric, size);
    }
}