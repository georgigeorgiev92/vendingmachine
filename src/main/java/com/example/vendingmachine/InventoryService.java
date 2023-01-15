/*
package com.example.vendingmachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;
    // check if the inventory is <10
    // if it is, update the quantity
    // if not, throw exception
    public Product addProduct(Product product) throws Exception {
        if(product.getName() == null)
            throw new Exception("Some value(s) are missing, Please check the manual properly");
        return productRepository.save(product);
    }

    // find the product in the inventory
    // update its details
    public Product updateProduct(Product product) throws Exception {
        Product product1 = getProduct(product.getId());
        if(product1 == null)
            throw new Exception("No such Beverage exist");
        return productRepository.save(product);
    }

    public Product getProduct(Long id) throws Exception {
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent())
            throw new Exception(("No Such Product exist"));
        return product.get();
    }

    // find the product in the inventory
    // remove it from the inventory
    public boolean removeProduct(Long id) throws Exception {
        Product product = getProduct(id);
        //ProductRepository.delete(product);
        return true;
    }


}
*/
