package com.example.vendingmachine.repos;

import com.example.vendingmachine.entities.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CoinRepo extends JpaRepository<Coin, Long> {
    Collection<Coin> findByVendingmachine(String name);

}
