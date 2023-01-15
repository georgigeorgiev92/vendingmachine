package com.example.vendingmachine;

//import com.example.vendingmachine.entities.Coin;
import com.example.vendingmachine.entities.Coin;
import com.example.vendingmachine.entities.Product;
import com.example.vendingmachine.entities.VendingMachine;
import com.example.vendingmachine.repos.CoinRepo;
import com.example.vendingmachine.repos.ProductRepo;
import com.example.vendingmachine.repos.VendingMachineRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class VendingMachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendingMachineApplication.class, args);
    }

    @Bean
    CommandLineRunner init(VendingMachineRepo vendingRepository,
                           ProductRepo productRepository,
                           CoinRepo coinRepository) {
        return (evt) -> Arrays.asList(
                        "Vending machine 1,Vending machine 2".split(","))
                .forEach(
                        a -> {
                            // Create the new machine
                            VendingMachine machine = vendingRepository.save(new VendingMachine(a, 0));

                            // Add 2 default products
                            productRepository.save(new Product(machine,
                                    "Coca Cola", 120,10));
                            productRepository.save(new Product(machine,
                                    "Sprite", 300, 5));

                            // Add some cash float to the machine
                            coinRepository.save(new Coin(machine, 50, 6));
                            coinRepository.save(new Coin(machine, 100, 2));
                            coinRepository.save(new Coin(machine, 20, 4));
                            coinRepository.save(new Coin(machine, 1, 10));
                        });
    }
}
