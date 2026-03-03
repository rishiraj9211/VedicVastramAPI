package org.example.vedicvastram.dto;

import lombok.Data;
import org.example.vedicvastram.entity.UserRole;
import org.example.vedicvastram.entity.UserStatus;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
    private UserRole role;
    private UserStatus status;
    private String phone;
    private String city;
}
