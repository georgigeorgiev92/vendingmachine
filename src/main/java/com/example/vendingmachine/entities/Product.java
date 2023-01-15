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


    @JsonIgnore
    @ManyToOne
    private VendingMachine vendingmachine;

    public Product(VendingMachine vendingMachine, String name, int cost, int quantity) {
        this.name = name;
        this.cost = cost;
        this.vendingmachine = vendingMachine;
        this.quantity = quantity;
    }
    public Product() {

    }


  /*  public VendingMachine getVendingMachineMachine() {
        return vendingmachine;
    }

    public void setVendingMachineMachine(VendingMachine vendingMachine) {
        this.vendingmachine = vendingMachine;
    }
*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
