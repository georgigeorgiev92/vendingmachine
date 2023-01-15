package com.example.vendingmachine.repos;

import com.example.vendingmachine.entities.VendingMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendingMachineRepo  extends JpaRepository<VendingMachine, Long> {
    Optional<VendingMachine> findByName(String name);

  //  VendingMachine findOneByName(String name);

}
