package com.example.vendingmachine;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    List<Product> products;
    int insertedCoins;

    VendingMachine() {
        products = new ArrayList<>();
    }

    void addProduct(Product product) {
        // check if the product is already in the inventory
        // if it is, update the quantity
        // if not, add it to the inventory
    }

    void updateProduct(Product product) {
        // find the product in the inventory
        // update its details
    }

    void removeProduct(Product product) {
        // find the product in the inventory
        // remove it from the inventory
    }


}
