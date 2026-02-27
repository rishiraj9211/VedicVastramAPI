package org.example.vedicvastram.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private Double price;

    private String availableSizes; // "S,M,L,XL"
    private String color;
    private String brand;
    private String fabric;

    private Integer quantity;

    private Long sellerId;

    @Enumerated(EnumType.STRING)
    private ProductStatus status; // PENDING, APPROVED, REJECTED
}