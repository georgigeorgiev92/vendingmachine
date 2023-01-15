package com.example.vendingmachine.entities;

import com.example.vendingmachine.entities.Coin;
import com.example.vendingmachine.entities.Product;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class VendingMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "vendingmachine")
    private Set<Product> productList = new HashSet<>();

    @OneToMany(mappedBy = "vendingmachine")
    private Set<Coin> coinsList = new HashSet<>();

    @Column
    public String name;
    public int currentAmount;
    public Long getID() {
        return id;
    }


    public Set<Product> getProducts() {
        return productList;
    }


    public VendingMachine(String name, int currentAmount) {
        this.name = name;
        this.currentAmount = currentAmount;
    }

    public VendingMachine() { //jpa only
    }


    public String getName() {
        return name;
    }


    public Set<Coin> getCoinsList() {
        return coinsList;
    }


    public int getCurrentAmount() {
        return currentAmount;
    }
}
