package org.example.vedicvastram.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {

    private String title;
    private String description;
    private Double price;
    private String availableSizes;
    private String color;
    private String brand;
    private String fabric;
    private Integer quantity;

    private List<String> imageUrls;
}
