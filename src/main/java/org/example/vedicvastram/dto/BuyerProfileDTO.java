package org.example.vedicvastram.dto;

import lombok.Data;

@Data
public class BuyerProfileDTO {
    private String name;
    private String email;
    private String phone;
    private String addressLine;
    private String city;
    private String state;
    private String pincode;
}
