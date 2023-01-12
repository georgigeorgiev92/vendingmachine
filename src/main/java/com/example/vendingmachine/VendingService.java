package com.example.vendingmachine;

import java.util.Optional;

import static com.example.vendingmachine.Coin.*;

public class VendingService {
    boolean coin_accepted = false;

    public Optional<Coin> insertCoin(Coin coin) {
        if (coin == TEN_STOTINKI || coin == TWENTY_STOTINKI || coin == FIFTY_STOTINKI || coin == ONE_LEV || coin == TWO_LEVA) {
            coin_accepted = true;
            return Optional.of(coin);

        } else {
            coin = null;
            return Optional.empty();
        }
    }

    void insertCoin(int coin) {
        // check if the coin is valid (10st, 20st, 50st, 1lv, 2lv)
        // if it is, add it to the insertedCoins
    }

    void reset() {
        // return the insertedCoins to the user
        // reset the insertedCoins to 0
    }

    void buyProduct(Product product) {
        // check if the product is in the inventory
        // check if the insertedCoins is equal or greater than the product's price
        // if both conditions are met, decrement the product's quantity by 1
        // reset the insertedCoins to 0
    }
}
