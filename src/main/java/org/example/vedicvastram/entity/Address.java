package org.example.vedicvastram.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Address {

    @Id @GeneratedValue
    private Long id;

    private Long buyerId;

    private String fullName;
    private String phone;
    private String addressLine;
    private String city;
    private String state;
    private String pincode;
}
