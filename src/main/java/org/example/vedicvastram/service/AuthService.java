package org.example.vedicvastram.service;

import org.example.vedicvastram.dto.LoginRequest;
import org.example.vedicvastram.dto.RegisterRequest;
import org.example.vedicvastram.entity.User;
import org.example.vedicvastram.entity.UserStatus;
import org.example.vedicvastram.respository.UserRepository;
import org.example.vedicvastram.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(RegisterRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setStatus(UserStatus.PENDING);
        user.setCreatedAt(LocalDateTime.now());

        repo.save(user);
        return "Registration successful. Awaiting Admin Approval.";
    }

    public String login(LoginRequest request) {
        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Email"));

//        if (!encoder.matches(request.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid Password");
//        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }
}