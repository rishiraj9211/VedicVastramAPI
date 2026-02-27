package org.example.vedicvastram.dto;

import lombok.Data;
import org.example.vedicvastram.entity.UserRole;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private UserRole role;
}
