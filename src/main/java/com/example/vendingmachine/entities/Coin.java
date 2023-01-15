package com.example.vendingmachine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Coin {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "coin_id", nullable = false)
    private Long id;

    @Column(name = "coin_value")
    public int value;


    @Column(name = "coin_amount")
    public int amount;

    @JsonIgnore
    public static final int[] POSSIBLE_VALUES = {200, 100, 50, 20, 10, 5, 2, 1};

    @JsonIgnore
    @ManyToOne
    private VendingMachine vendingmachine;

    public Coin(VendingMachine machine, int value, int amount) {
    }

    public Coin() {

    }

}
