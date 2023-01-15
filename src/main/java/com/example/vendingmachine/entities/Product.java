package com.example.vendingmachine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id", nullable = false)
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public VendingMachine getVendingmachine() {
        return vendingmachine;
    }

    public void setVendingmachine(VendingMachine vendingmachine) {
        this.vendingmachine = vendingmachine;
    }

    @Column(name = "product_name")
    public String name;
    @Column(name = "product_cost")
    public int price;
    @Column(name = "product_quantity")
    public int quantity;


    @JsonIgnore
    @ManyToOne
    private VendingMachine vendingmachine;

    public Product(VendingMachine vendingMachine, String name, int price, int quantity) {
        this.name = name;
        this.price = price;
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
