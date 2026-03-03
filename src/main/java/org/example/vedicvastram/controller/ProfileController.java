package org.example.vedicvastram.controller;

import org.example.vedicvastram.dto.BuyerProfileDTO;
import org.example.vedicvastram.dto.SellerProfileDTO;
import org.example.vedicvastram.service.CustomUserDetails;
import org.example.vedicvastram.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    private ProfileService service;

    @GetMapping("/buyer/profile")
    public BuyerProfileDTO getBuyerProfile(@AuthenticationPrincipal CustomUserDetails user) {
        return service.getBuyerProfile(user.getUser().getId());
    }

    @PutMapping("/buyer/profile")
    public String updateBuyerProfile(@AuthenticationPrincipal CustomUserDetails user,
                                     @RequestBody BuyerProfileDTO dto) {
        return service.updateBuyerProfile(user.getUser().getId(), dto);
    }

    @GetMapping("/seller/profile")
    public SellerProfileDTO getSellerProfile(@AuthenticationPrincipal CustomUserDetails user) {
        return service.getSellerProfile(user.getUser().getId());
    }

    @PutMapping("/seller/profile")
    public String updateSellerProfile(@AuthenticationPrincipal CustomUserDetails user,
                                      @RequestBody SellerProfileDTO dto) {
        return service.updateSellerProfile(user.getUser().getId(), dto);
    }
}
