package com.example.vendingmachine.controllers;

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


   // @RequestMapping(method = RequestMethod.GET)
    @GetMapping("/vendingmachine")
    List<VendingMachine> getMachine() {
        return this.vendingRepo.findAll();
    }


    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping("/vendingmachine")
    ResponseEntity<?> addMachine(@RequestBody VendingMachine input) {

        VendingMachine result = this.vendingRepo.save(new VendingMachine(input.name, input.currentAmount));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getID()).toUri();

        return ResponseEntity.created(location).build();

    }


    @GetMapping("/{vendingMachineId}/products")
    Collection<Product> getAllProducts(@PathVariable String vendingMachineId) {
        //return this.productRepo.findByVendingmachine(vendingMachineId);
        return productRepo.findAll();

    }
    @PostMapping("/{vendingMachine}/products")//@RequestMapping(method = RequestMethod.POST)
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

    @PutMapping("/{vendingMachine}/products/{id}")
    public ResponseEntity<Product> updateProductInventory(@PathVariable("id") long id, @RequestBody Product product) {
        Optional<Product> tutorialData = productRepo.findById(id);

        if (tutorialData.isPresent()&& product.quantity<10)  {
            Product _prod = tutorialData.get();
            _prod.setName(product.getName());
            _prod.setPrice(product.getPrice());
            _prod.setQuantity(product.getQuantity() + 1);
            return new ResponseEntity<>(productRepo.save(_prod), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{vendingMachine}/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        try {
            productRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{vendingMachine}/products/{productId}")
    //@RequestMapping(method = RequestMethod.GET, value = "/{vendingMachine}/products/{productId}")
    Product buyProduct(@PathVariable String vendingMachine, @PathVariable Long productId) {

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



    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping ("/{vendingMachine}/coins")
    Optional<?> addCoin(@PathVariable String machineId, @RequestBody Coin coin) {

        return this.vendingRepo
                .findByName(machineId)
                .map(machine -> {
                     boolean coinFound = false;


                    // Else add the coin to the repository
                    if (!coinFound && IntStream.of(coin.POSSIBLE_VALUES).anyMatch(x -> x == coin.value)) {
                        this.coinRepo.saveAndFlush(new Coin(machine, coin.value));
                        coinFound = true;
                    }

                    if (coinFound) {
                        machine.currentAmount += coin.value;
                    }

                    this.vendingRepo.saveAndFlush(machine);

                    return this.vendingRepo.findByName(machineId);
                });
    }

   // @RequestMapping(method = RequestMethod.GET, value = "/refund")
    @GetMapping("/{vendingMachine}/coin/reset")

    Coin reset(@PathVariable String machineId) {

        final int[] refundTotal = new int[1];

        this.vendingRepo.findByName(machineId).map(machine -> {
            refundTotal[0] = machine.currentAmount;
            return refundTotal[0];
        });

        Coin refundCoin = new Coin();

        for (int value : Coin.POSSIBLE_VALUES) {
            this.coinRepo.findByVendingmachine(machineId).forEach(coin -> {
                if (value == coin.value && coin.value <= refundTotal[0]) {
                    this.coinRepo.saveAndFlush(coin);
                  //  refundCoin.add(new Coin(coin.value, (int) max_coins));
                  //  refundTotal[0] -= (int) max_coins * coin.value;
                }
            });
        }

        return refundCoin;
    }
}
