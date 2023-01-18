package com.example.vendingmachine.repos;

import com.example.vendingmachine.entities.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
@Component
@Repository
@Transactional
public interface CoinRepo extends JpaRepository<Coin, Long> {

    Collection<Coin> findByVendingmachine(String name);

    Coin findByid(Long id);
}
