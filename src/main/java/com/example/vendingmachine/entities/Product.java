package com.example.vendingmachine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "product_name")
    public String name;
    @Column(name = "product_cost")
    public int cost;
    @Column(name = "product_quantity")
    public int quantity;


    @ManyToOne
    @JsonIgnore
    // @JoinColumn(name = "machine_id")
    private VendingMachine vendingmachine;

    public Product(VendingMachine machine, String name, int i, int i1) {
    }

    public Product() {

    }


    public VendingMachine getVendingMachineMachine() {
        return vendingmachine;
    }

    public void setVendingMachineMachine(VendingMachine vendingMachine) {
        this.vendingmachine = vendingMachine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
