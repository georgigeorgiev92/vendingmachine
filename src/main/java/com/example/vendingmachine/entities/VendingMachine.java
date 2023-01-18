package com.example.vendingmachine.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class VendingMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "machine_id", nullable = false)
    private Long id;

    @Column(name = "machine_name")
    public String name;

    @Column(name = "machine_amount")
    public int currentAmount;

    @OneToMany(mappedBy = "vendingmachine")
    private Set<Product> productList = new HashSet<>();

    @OneToMany(mappedBy = "vendingmachine")
    private Set<Coin> coinsList = new HashSet<>();

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
