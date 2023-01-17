/*
package com.example.vendingmachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class VendingController {
    @Autowired
    private final ProductRepository repository;

    private InventoryService inventoryService;

    public VendingController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products")
    List<Product> all() {
        return repository.findAll();
    }


*/
/*

    @PostMapping("/products/add")
    public ResponseEntity addProduct(@RequestBody Product product) {
        try {
            Product prod = inventoryService.addProduct(product);
            return correctResponse(prod, HttpStatus.OK,HttpStatus.OK.value(),"Success",HttpStatus.OK);
        }
        catch (Exception ex) {
            return errorResponse(ex);
        }
    }
*//*


    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            Product _prod = repository
                    .save(new Product(product.getName(), product.getPrice(), product.getQuantity()));
            return new ResponseEntity<>(_prod, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
*/
