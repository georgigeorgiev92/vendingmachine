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
        //jpa only
    }

}
