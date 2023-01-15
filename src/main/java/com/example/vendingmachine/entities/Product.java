package com.example.vendingmachine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Product {

    @JsonIgnore
    @ManyToOne
    private VendingMachine vendingMachine;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private VendingMachine vendingmachine;



    public VendingMachine getVendingMachineMachine() {
        return vendingMachine;
    }

    public void setVendingMachineMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
