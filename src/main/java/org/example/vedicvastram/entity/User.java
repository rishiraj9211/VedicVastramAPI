package org.example.vedicvastram.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;
    private String addressLine;
    private String city;
    private String state;
    private String pincode;

    // Seller specific
    private String storeName;
    private String gst;

    @Enumerated(EnumType.STRING)
    private UserRole role; // ADMIN, SELLER, BUYER

    @Enumerated(EnumType.STRING)
    private UserStatus status; // PENDING, APPROVED, REJECTED

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
