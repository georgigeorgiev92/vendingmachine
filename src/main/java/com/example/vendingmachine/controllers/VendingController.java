package com.example.vendingmachine.controllers;

import com.example.vendingmachine.entities.Coin;
import com.example.vendingmachine.entities.Product;
import com.example.vendingmachine.entities.VendingMachine;
import com.example.vendingmachine.repos.CoinRepo;
import com.example.vendingmachine.repos.ProductRepo;
import com.example.vendingmachine.repos.VendingMachineRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
    ResponseEntity<?> addProduct(@PathVariable String vendingMachine, @RequestBody Product input) {

        return this.vendingRepo
                .findByName(vendingMachine)
                .map(machine -> {
                    Product result = productRepo.save(new Product(machine, input.name, input.cost, input.quantity));

                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri();

                    return ResponseEntity.created(location).build();
                })
                .orElse(ResponseEntity.noContent().build());

    }
    @GetMapping("/{vendingMachine}/products/{productId}")
    //@RequestMapping(method = RequestMethod.GET, value = "/{vendingMachine}/products/{productId}")
    Product buyProduct(@PathVariable String vendingMachine, @PathVariable Long productId) {

        final Product[] soldProduct = new Product[1];

        this.vendingRepo.findByName(vendingMachine).map(machine -> {

            this.productRepo.findByVendingmachine(vendingMachine)
                    .forEach(product -> {
                        if (Objects.equals(product.getId(), productId)
                                && product.cost <= machine.currentAmount
                                && product.quantity > 0) {
                            product.quantity--;
                            this.productRepo.saveAndFlush(product);
                            machine.currentAmount -= product.cost;
                            product.quantity = 1;
                            soldProduct[0] = product;
                        }
                    });

            this.vendingRepo.saveAndFlush(machine);
            return machine;
        });

        return soldProduct[0];
    }

    @GetMapping("/{vendingMachine}/coins")
    Collection<Coin> getCoins(@PathVariable String machineId) {
        return this.coinRepo.findByVendingmachine(machineId);
    }

    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping ("/{vendingMachine}/coins")
    Optional<?> addCoin(@PathVariable String machineId, @RequestBody Coin coin) {

        return this.vendingRepo
                .findByName(machineId)
                .map(machine -> {
                    final boolean[] coinFound = {false};

                    // Increase the amount of coins if the machine already has seen that coin
                    this.coinRepo.findByVendingmachine(machineId).forEach(machineCoin -> {
                        if (coin.value == machineCoin.value) {
                            coin.amount++;
                            coinFound[0] = true;
                        }
                    });

                    // Else add the coin to the repository
                    if (!coinFound[0] && IntStream.of(coin.POSSIBLE_VALUES).anyMatch(x -> x == coin.value)) {
                        this.coinRepo.saveAndFlush(new Coin(machine, coin.value, 1));
                        coinFound[0] = true;
                    }

                    if (coinFound[0]) {
                        machine.currentAmount += coin.value;
                    }

                    this.vendingRepo.saveAndFlush(machine);

                    return this.vendingRepo.findByName(machineId);
                });
    }

   // @RequestMapping(method = RequestMethod.GET, value = "/refund")
    @PostMapping ("/{vendingMachine}/coins/refund")

    List<Coin> refundCoins(@PathVariable String machineId) {

        final int[] refundTotal = new int[1];

        this.vendingRepo.findByName(machineId).map(machine -> {
            refundTotal[0] = machine.currentAmount;
            return refundTotal[0];
        });

        List<Coin> refundCoins = new ArrayList<>();

        for (int value : Coin.POSSIBLE_VALUES) {
            this.coinRepo.findByVendingmachine(machineId).forEach(coin -> {
                if (value == coin.value && coin.amount > 0 && coin.value <= refundTotal[0]) {
                    double max_coins = Math.min(Math.floor(refundTotal[0] / coin.value), coin.amount);
                    coin.amount -= max_coins;
                    this.coinRepo.saveAndFlush(coin);
                    //refundCoins.add(new Coin(coin.value, (int) max_coins));
                    refundTotal[0] -= (int) max_coins * coin.value;
                }
            });
        }

        return refundCoins;
    }
}
