package org.example.vedicvastram.service;

import org.example.vedicvastram.dto.BuyerProfileDTO;
import org.example.vedicvastram.dto.SellerProfileDTO;
import org.example.vedicvastram.entity.User;
import org.example.vedicvastram.entity.UserStatus;
import org.example.vedicvastram.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepo;

    public BuyerProfileDTO getBuyerProfile(Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        BuyerProfileDTO dto = new BuyerProfileDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddressLine(user.getAddressLine());
        dto.setCity(user.getCity());
        dto.setState(user.getState());
        dto.setPincode(user.getPincode());
        return dto;
    }

    public String updateBuyerProfile(Long userId, BuyerProfileDTO dto) {
        User user = userRepo.findById(userId).orElseThrow();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddressLine(dto.getAddressLine());
        user.setCity(dto.getCity());
        user.setState(dto.getState());
        user.setPincode(dto.getPincode());
        userRepo.save(user);
        return "Profile updated";
    }

    public SellerProfileDTO getSellerProfile(Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        SellerProfileDTO dto = new SellerProfileDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setStatus(user.getStatus() != null ? user.getStatus().name() : UserStatus.PENDING.name());
        dto.setStoreName(user.getStoreName());
        dto.setGst(user.getGst());
        dto.setAddressLine(user.getAddressLine());
        dto.setCity(user.getCity());
        dto.setState(user.getState());
        dto.setPincode(user.getPincode());
        return dto;
    }

    public String updateSellerProfile(Long userId, SellerProfileDTO dto) {
        User user = userRepo.findById(userId).orElseThrow();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setStoreName(dto.getStoreName());
        user.setGst(dto.getGst());
        user.setAddressLine(dto.getAddressLine());
        user.setCity(dto.getCity());
        user.setState(dto.getState());
        user.setPincode(dto.getPincode());
        userRepo.save(user);
        return "Profile updated";
    }
}
