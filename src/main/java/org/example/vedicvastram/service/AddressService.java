package org.example.vedicvastram.service;

import org.example.vedicvastram.dto.AddressDTO;
import org.example.vedicvastram.entity.Address;
import org.example.vedicvastram.respository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repo;

    public String add(Long buyerId, AddressDTO dto) {
        Address a = new Address();
        a.setBuyerId(buyerId);
        a.setFullName(dto.getFullName());
        a.setPhone(dto.getPhone());
        a.setAddressLine(dto.getAddressLine());
        a.setCity(dto.getCity());
        a.setState(dto.getState());
        a.setPincode(dto.getPincode());
        repo.save(a);
        return "Address added.";
    }

    public List<Address> list(Long buyerId) {
        return repo.findByBuyerId(buyerId);
    }

    public String delete(Long id) {
        repo.deleteById(id);
        return "Address removed.";
    }
}
