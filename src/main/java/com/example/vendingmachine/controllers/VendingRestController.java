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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("")
public class VendingRestController {
    private final ProductRepo productRepository;
    private final VendingMachineRepo vendingRepository;
    private final CoinRepo coinRepository;


    @Autowired
    VendingRestController(ProductRepo productRepository,
                          VendingMachineRepo vendingRepository,
                          CoinRepo coinRepository) {
        this.productRepository = productRepository;
        this.vendingRepository = vendingRepository;
        this.coinRepository = coinRepository;
    }


    @RequestMapping(method = RequestMethod.GET)
    List<VendingMachine> getMachines() {
        return this.vendingRepository.findAll();
    }




    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody VendingMachine input) {

        VendingMachine result = this.vendingRepository.save(new VendingMachine(input.name, input.currentAmount));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getID()).toUri();

        return ResponseEntity.created(location).build();

    }


}
