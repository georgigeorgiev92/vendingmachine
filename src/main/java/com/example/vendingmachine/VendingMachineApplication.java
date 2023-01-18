package com.example.vendingmachine;


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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.vendingmachine.controller", "com.example.vendingmachine.entities", "com.example.vendingmachine.repos"})
@EnableJpaRepositories(basePackages = {"com.example.vendingmachine.repos*"})
public class VendingMachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendingMachineApplication.class, args);
    }

    @Bean
    CommandLineRunner init(VendingMachineRepo vendingRepository,
                           ProductRepo productRepository,
                           CoinRepo coinRepository) {

        //Add new vendingmachine, product and coin
        VendingMachine vendingMachine = vendingRepository.save(new VendingMachine("Coca-Cola Company", 0));

        productRepository.save(new Product(vendingMachine, "Fanta", 100, 10));
        productRepository.save(new Product(vendingMachine, "Sprite", 200, 5));

        coinRepository.save(new Coin(vendingMachine, 50));
        return null;
    }
}
