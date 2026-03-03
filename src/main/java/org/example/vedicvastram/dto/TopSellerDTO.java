package org.example.vedicvastram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopSellerDTO {
    private String name;
    private String revenue;
    private Integer orders;
}
