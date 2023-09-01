package com.example.alimjivanifinalproject;

public class CartItem {
    private String name;
    private double price;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;


    public CartItem(String name, double price, String image, String id, int quantity) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int increaseQuantity() {
        quantity++;
        return quantity;
    }

    public int decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
        }
        return quantity;
    }
}
