package com.restaurant.model;

public class Restaurant {
    private int id;
    private String name;
    private City city;
    private int chefSpecialDishId;

    public Restaurant() {
    }

    public Restaurant(String name, City city, int chefSpecialDishId) {
        this.name = name;
        this.city = city;
        this.chefSpecialDishId = chefSpecialDishId;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getChefSpecialDishId() {
        return chefSpecialDishId;
    }

    public void setChefSpecialDishId(int chefSpecialDishId) {
        this.chefSpecialDishId = chefSpecialDishId;
    }
}

