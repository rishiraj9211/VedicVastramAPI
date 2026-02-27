package org.example.vedicvastram.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String fullName;
    private String phone;
    private String addressLine;
    private String city;
    private String state;
    private String pincode;
}
