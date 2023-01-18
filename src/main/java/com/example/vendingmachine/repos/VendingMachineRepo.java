package com.example.vendingmachine.repos;

import com.example.vendingmachine.entities.VendingMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Component
@Repository
@Transactional
public interface VendingMachineRepo  extends JpaRepository<VendingMachine, Long> {
    Optional<VendingMachine> findByName(String name);

  //  VendingMachine findOneByName(String name);

}
