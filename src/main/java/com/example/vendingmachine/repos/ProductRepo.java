package com.example.vendingmachine.repos;

import com.example.vendingmachine.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
@Component
@Repository
@Transactional
public interface ProductRepo extends JpaRepository<Product, Long> {
    Collection<Product> findByVendingmachine(String name);

}
