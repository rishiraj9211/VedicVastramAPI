package org.example.vedicvastram.service;

import org.example.vedicvastram.entity.Product;
import org.example.vedicvastram.entity.ProductStatus;
import org.example.vedicvastram.respository.ProductImageRepository;
import org.example.vedicvastram.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private ProductImageRepository images;

    public List<Product> getAllApproved() {
        return repo.findByStatus(ProductStatus.APPROVED);
    }

    public Product getById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public List<Product> filter(String brand, String color, String fabric, String size) {
        return repo.filterProducts(brand, color, fabric, size);
    }
}