package com.example.alimjivanifinalproject;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    public static CartManager instance;
    public List<CartItem> cartItems;

    public CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addToCart(CartItem newItem) {

        for (CartItem existingItem : cartItems) {
            if (existingItem.getId().equals(newItem.getId())) {
                existingItem.increaseQuantity();
                return;
            }
        }
        cartItems.add(newItem);
    }

    public double calculateTotal() {

        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

}