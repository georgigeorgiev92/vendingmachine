package com.example.vendingmachine.controller;

import com.example.vendingmachine.entities.Coin;
import com.example.vendingmachine.entities.Product;
import com.example.vendingmachine.entities.VendingMachine;
import com.example.vendingmachine.repos.CoinRepo;
import com.example.vendingmachine.repos.ProductRepo;
import com.example.vendingmachine.repos.VendingMachineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.IntStream;


@RestController
@RequestMapping("")
public class VendingController {
    private final ProductRepo productRepo;
    private final VendingMachineRepo vendingRepo;
    private final CoinRepo coinRepo;


    @Autowired
    VendingController(ProductRepo productRepo,
                      VendingMachineRepo vendingRepo,
                      CoinRepo coinRepo) {
        this.productRepo = productRepo;
        this.vendingRepo = vendingRepo;
        this.coinRepo = coinRepo;
    }

    //shows all vending machines
    @GetMapping("/vendingmachine")
    List<VendingMachine> getMachine() {
        return this.vendingRepo.findAll();
    }

    //adds a vending machine
    @PostMapping("/vendingmachine")
    public ResponseEntity<?> addMachine(@RequestBody VendingMachine input) {

        VendingMachine result = this.vendingRepo.save(new VendingMachine(input.name, input.currentAmount));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getID()).toUri();

        return ResponseEntity.created(location).build();

    }

    //shows all products in a vending machine
    @GetMapping("/{vendingMachineId}/products")
    Collection<Product> getAllProducts(@PathVariable String vendingMachineId) {
        //return this.productRepo.findByVendingmachine(vendingMachineId);
        return productRepo.findAll();

    }

    //adds a product to a vending machine
    @PostMapping("/{vendingMachine}/products")
    ResponseEntity<?> addNewProduct(@PathVariable String vendingMachine, @RequestBody Product input) {

        return this.vendingRepo
                .findByName(vendingMachine)
                .map(machine -> {
                    Product result = productRepo.save(new Product(machine, input.name, input.price, input.quantity));

                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri();

                    return ResponseEntity.created(location).build();
                })
                .orElse(ResponseEntity.noContent().build());

    }

    //updates the product inventory if needed
    @PutMapping("/{vendingMachine}/products/{id}")
    public ResponseEntity<Product> updateProductInventory(@PathVariable("id") long id, @RequestBody Product product) {
        Optional<Product> productInvent = productRepo.findById(id);

        if (productInvent.isPresent() && product.quantity < 10) {
            Product _prod = productInvent.get();
            _prod.setName(product.getName());
            _prod.setPrice(product.getPrice());
            _prod.setQuantity(product.getQuantity() + 1);
            return new ResponseEntity<>(productRepo.save(_prod), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //deletes a product from the inventory
    @DeleteMapping("/{vendingMachine}/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        try {
            productRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //adds a coin to the vendingmachine
    @PutMapping("/{vendingMachine}/coins/{id}")
    public ResponseEntity<Coin> addNewCoin(@PathVariable("id") long id, @RequestBody Coin coin) {
        Optional<Coin> coinEvent = coinRepo.findById(id);

        if (coinEvent.isPresent() && IntStream.of(coin.POSSIBLE_VALUES).anyMatch(x -> x == coin.value)){
            Coin _co = coinEvent.get();
            _co.setValue(coin.getValue());
            return new ResponseEntity<>(coinRepo.save(_co), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //obsolete
    @PostMapping ("/{vendingMachine}/coins")
    Optional<?> addCoin(@PathVariable String vendingMachine, @RequestBody Coin coin) {

        return this.vendingRepo
                .findByName(vendingMachine)
                .map(machine -> {
                    boolean coinFound = false;

                    if (!coinFound && IntStream.of(coin.POSSIBLE_VALUES).anyMatch(x -> x == coin.value)) {
                        this.coinRepo.saveAndFlush(new Coin(machine, coin.value));
                        coinFound = true;
                    }

                    if (coinFound) {
                        machine.currentAmount += coin.value;
                    }

                    this.vendingRepo.saveAndFlush(machine);

                    return this.vendingRepo.findByName(vendingMachine);
                });
    }

    //buy a product and decrement the inventory by 1
    @GetMapping("/{vendingMachine}/products/{productId}")
    public Product buyProduct(@PathVariable String vendingMachine, @PathVariable Long productId) {

        final Product[] soldProduct = new Product[1];

        this.vendingRepo.findByName(vendingMachine).map(machine -> {

            this.productRepo.findByVendingmachine(vendingMachine)
                    .forEach(product -> {
                        if (Objects.equals(product.getId(), productId)
                                && product.price <= machine.currentAmount
                                && product.quantity > 0) {
                            product.quantity--;
                            this.productRepo.saveAndFlush(product);
                            machine.currentAmount -= product.price;
                            product.quantity = 1;
                            soldProduct[0] = product;
                        }
                    });

            this.vendingRepo.saveAndFlush(machine);
            return machine;
        });
        return soldProduct[0];
    }

    //refunds and deletes coin from vending machine
    @DeleteMapping("/{vendingMachine}/coins/{id}")
    public ResponseEntity<HttpStatus> refund(@PathVariable("id") long id) {
        try {
            coinRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //obsolete
    @GetMapping("/{vendingMachine}/coins/reset")
    List<Coin>  reset(@PathVariable String vendingMachine) {

        final int[] refunds = new int[1];

        this.vendingRepo.findByName(vendingMachine).map(machine -> {
            refunds[0] = machine.currentAmount;
            return refunds[0];
        });

        List<Coin> refundCoins = new ArrayList<>();

        for (int value : Coin.POSSIBLE_VALUES) {
            this.coinRepo.findByVendingmachine(vendingMachine).forEach(coin -> {
                if (value == coin.value && coin.value <= refunds[0]) {
                    this.coinRepo.saveAndFlush(coin);
                    refundCoins.add(new Coin(coin.value));
                }
            });
        }

        return refundCoins;
    }
}
