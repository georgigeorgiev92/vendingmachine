package com.example.vendingmachine.controllers;

import com.example.vendingmachine.entities.VendingMachine;
import com.example.vendingmachine.repos.CoinRepo;
import com.example.vendingmachine.repos.ProductRepo;
import com.example.vendingmachine.repos.VendingMachineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

public class VendingController {

    private final VendingMachineRepo vendingMachineRepo;
    private final CoinRepo coinRepo;
    private final ProductRepo productRepo;


     VendingController(VendingMachineRepo vendingMachineRepo,
                             CoinRepo coinRepo, ProductRepo productRepo) {
        this.vendingMachineRepo = vendingMachineRepo;
        this.coinRepo = coinRepo;
        this.productRepo = productRepo;
    }


    @RequestMapping(method = RequestMethod.GET)
    List<VendingMachine> getMachines() {
        return this.vendingMachineRepo.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addMachine(@RequestBody VendingMachine vendingMachine) {

        VendingMachine result =
                this.vendingMachineRepo.save(new VendingMachine(vendingMachine.name, vendingMachine.currentAmount));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getID()).toUri();

        return ResponseEntity.created(location).build();

    }

/*    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addProduct(@PathVariable String machineId, @RequestBody Product input) {
        this.validateMachine(machineId);

        return this.vendingRepository
                .findByName(machineId)
                .map(machine -> {
                    Product result = productRepository.save(new Product(machine, input.name, input.cost, input.quantity));

                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri();

                    return ResponseEntity.created(location).build();
                })
                .orElse(ResponseEntity.noContent().build());

    }
    private void validateMachine(String machineId) {
        this.vendingRepository.findByName(machineId).orElseThrow(
                () -> new MachineNotFoundException(machineId));
    }*/

}
