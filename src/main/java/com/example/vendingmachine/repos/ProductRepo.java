package com.example.vendingmachine.repos;

import com.example.vendingmachine.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Collection<Product> findByVendingmachine(String name);

}
