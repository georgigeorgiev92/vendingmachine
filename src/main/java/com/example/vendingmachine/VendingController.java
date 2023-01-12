package com.example.vendingmachine;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

@RestController
public class VendingController {

    private final ProductRepository repository;

    private InventoryService inventoryService;
    public VendingController(ProductRepository repository) {
        this.repository = repository;
    }
/*
    @GetMapping("/products")
    List<Product> all() {
        return repository.findAll();
    }
    @PostMapping("/products/add")
    List<Product> addProduct() {
        return repository.findAll();
    }

    @PostMapping("/products/update")
    List<Product> updateProduct() {
        return repository.findAll();
    }*/



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
    public ResponseEntity correctResponse(Object value, Object error, int statusCode, String message, HttpStatus status) {
        HashMap<Object, Object> response = new HashMap<>();
        response.put("value", value);
        response.put("error", error);
        response.put("status", statusCode);
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
    public ResponseEntity errorResponse(Exception ex) {
        HashMap<Object, Object> response = new HashMap<>();
        response.put("timestamp", new Date());
        response.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
