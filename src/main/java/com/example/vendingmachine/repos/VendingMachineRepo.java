package com.example.vendingmachine.repos;

import com.example.vendingmachine.entities.VendingMachine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendingMachineRepo  extends JpaRepository<VendingMachine, Long> {
    VendingMachine findOneByName(String name);

}
