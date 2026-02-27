package org.example.vedicvastram.controller;

import org.example.vedicvastram.dto.AddressDTO;
import org.example.vedicvastram.entity.Address;
import org.example.vedicvastram.service.AddressService;
import org.example.vedicvastram.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyer/address")
public class AddressController {

    @Autowired
    private AddressService service;

    @PostMapping
    public String add(@AuthenticationPrincipal CustomUserDetails user,
                      @RequestBody AddressDTO dto) {
        return service.add(user.getUser().getId(), dto);
    }

    @GetMapping
    public List<Address> list(@AuthenticationPrincipal CustomUserDetails user) {
        return service.list(user.getUser().getId());
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }
}