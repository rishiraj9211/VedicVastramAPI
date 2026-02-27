package org.example.vedicvastram.dto;

import lombok.Data;
import org.example.vedicvastram.entity.PaymentType;

@Data
public class OrderRequest {

    private Long addressId;
    private PaymentType paymentType;  // COD / ONLINE
}
