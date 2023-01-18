# Vending machine


#
The following actions can be performed

```
localhost:8080/vendingmachine  

    GET: View the vending machine details
    
    POST: Add a new vending machine to the system
```
```
localhost:8080/{vendingMachineId}/products
    
    GET: List all the products in the vending machine
    
    POST: Add a new product 
    
    PUT: Update a product inventory 
    
    DELETE: Delete a product from the inventory
```
```
localhost:8080/{vendingMachineId}/products/{productId}
   
    GET:Buy a product and decrement the inventory by 1

```
```
localhost:8080/{vendingMachineId}/coins  
    
    PUT: Add a coin to the vending machine
    or
    POST: Put a coin in the machine.
    
    DELETE: Remove a coin from the machine
```
```
localhost:8080/{vendingMachineId}/coins/reset

    GET: Refunds a coin
```
```
