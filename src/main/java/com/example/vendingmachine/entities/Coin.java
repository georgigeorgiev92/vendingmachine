package com.example.vendingmachine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Coin {


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public VendingMachine getVendingmachine() {
        return vendingmachine;
    }

    public void setVendingmachine(VendingMachine vendingmachine) {
        this.vendingmachine = vendingmachine;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "coin_id", nullable = false)
    private Long id;

    @Column(name = "coin_value")
    public int value;



    @JsonIgnore
    public static final int[] POSSIBLE_VALUES = {10, 20, 50, 100, 200};

    @JsonIgnore
    @ManyToOne
    private VendingMachine vendingmachine;

    public Coin(VendingMachine vendingmachine, int value) {
        this.vendingmachine = vendingmachine;
        this.value = value;
    }


    public Coin(int value){
        this.value = value;
    }

    public Coin(){
    }

}
