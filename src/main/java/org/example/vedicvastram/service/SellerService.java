package org.example.vedicvastram.service;

import org.example.vedicvastram.dto.ProductDTO;
import org.example.vedicvastram.entity.Product;
import org.example.vedicvastram.entity.ProductImage;
import org.example.vedicvastram.entity.ProductStatus;
import org.example.vedicvastram.respository.ProductImageRepository;
import org.example.vedicvastram.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ProductImageRepository imageRepo;

    public String addProduct(ProductDTO dto, Long sellerId) {

        Product p = new Product();
        p.setTitle(dto.getTitle());
        p.setPrice(dto.getPrice());
        p.setDescription(dto.getDescription());
        p.setAvailableSizes(dto.getAvailableSizes());
        p.setColor(dto.getColor());
        p.setBrand(dto.getBrand());
        p.setFabric(dto.getFabric());
        p.setQuantity(dto.getQuantity());
        p.setSellerId(sellerId);
        p.setStatus(ProductStatus.PENDING);

        productRepo.save(p);

        dto.getImageUrls().forEach(url -> {
            ProductImage img = new ProductImage();
            img.setProductId(p.getId());
            img.setUrl(url);
            imageRepo.save(img);
        });

        return "Product added. Awaiting admin approval.";
    }

    public List<Product> getMyProducts(Long sellerId) {
        return productRepo.findBySellerId(sellerId);
    }
}