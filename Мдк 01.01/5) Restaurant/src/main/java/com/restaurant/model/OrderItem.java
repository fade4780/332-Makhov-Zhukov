package com.restaurant.model;

public class OrderItem {
    private Dish dish;
    private Drink drink;
    private int quantity;
    private double price;

    public OrderItem(Dish dish, int quantity, double price) {
        this.dish = dish;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItem(Drink drink, int quantity, double price) {
        this.drink = drink;
        this.quantity = quantity;
        this.price = price;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return price * quantity;
    }

    public boolean isDish() {
        return dish != null;
    }
}

