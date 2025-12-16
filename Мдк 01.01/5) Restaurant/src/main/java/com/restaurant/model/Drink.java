package com.restaurant.model;

public class Drink {
    private int id;
    private String name;
    private int calories;
    private boolean alcoholic;
    private double basePrice;

    public Drink() {
    }

    public Drink(String name, int calories, boolean alcoholic, double basePrice) {
        this.name = name;
        this.calories = calories;
        this.alcoholic = alcoholic;
        this.basePrice = basePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public boolean isAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        this.alcoholic = alcoholic;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getPrice(City city) {
        return basePrice * city.getPriceMultiplier();
    }
}

