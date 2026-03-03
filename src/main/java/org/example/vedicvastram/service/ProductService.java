package org.example.vedicvastram.service;

import org.example.vedicvastram.entity.Product;
import org.example.vedicvastram.entity.ProductStatus;
import org.example.vedicvastram.entity.ProductImage;
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
        return repo.findByStatus(ProductStatus.APPROVED)
                .stream()
                .map(this::withImages)
                .toList();
    }

    public Product getById(Long id) {
        return withImages(repo.findById(id).orElseThrow());
    }

    public List<Product> filter(String brand, String color, String fabric, String size) {
        return repo.filterProducts(ProductStatus.APPROVED, brand, color, fabric, size)
                .stream()
                .map(this::withImages)
                .toList();
    }

    private Product withImages(Product product) {
        List<String> urls = images.findByProductId(product.getId())
                .stream()
                .map(ProductImage::getUrl)
                .toList();
        product.setImageUrls(urls);
        return product;
    }
}
