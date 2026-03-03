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
@RequestMapping({"/buyer/address", "/buyer/addresses"})
public class AddressController {

    @Autowired
    private AddressService service;

    @PostMapping({"", "/", "/add"})
    public String add(@AuthenticationPrincipal CustomUserDetails user,
                      @RequestBody AddressDTO dto) {
        return service.add(user.getUser().getId(), dto);
    }

    @GetMapping({"", "/", "/list"})
    public List<Address> list(@AuthenticationPrincipal CustomUserDetails user) {
        return service.list(user.getUser().getId());
    }

    @PutMapping("/{id}")
    public String update(@AuthenticationPrincipal CustomUserDetails user,
                         @PathVariable Long id,
                         @RequestBody AddressDTO dto) {
        return service.update(user.getUser().getId(), id, dto);
    }

    @PutMapping("/update")
    public String updateAlt(@AuthenticationPrincipal CustomUserDetails user,
                            @RequestParam Long id,
                            @RequestBody AddressDTO dto) {
        return service.update(user.getUser().getId(), id, dto);
    }

    @DeleteMapping({"/{id}", "/remove/{id}"})
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
