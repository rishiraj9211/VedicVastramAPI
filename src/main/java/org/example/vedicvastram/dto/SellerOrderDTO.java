package org.example.vedicvastram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerOrderDTO {
    private Long id;
    private String product;
    private String customer;
    private Double amount;
    private String status;
}
