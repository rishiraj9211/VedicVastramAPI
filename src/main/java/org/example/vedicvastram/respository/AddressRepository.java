package org.example.vedicvastram.respository;

import org.example.vedicvastram.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByBuyerId(Long buyerId);
}
