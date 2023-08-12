package com.example.alimjivanifinalproject;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    public static CartManager instance;
    public List<CartItem> cartItems;
    public String totalPayment;

    public CartManager() {
        cartItems = new ArrayList<>();
        totalPayment = "0";
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
                totalPayment += existingItem.getPrice();
                return;
            }
        }
        cartItems.add(newItem);
        totalPayment += newItem.getPrice();
    }

    public String getTotalPayment() {
        return totalPayment;
    }
}