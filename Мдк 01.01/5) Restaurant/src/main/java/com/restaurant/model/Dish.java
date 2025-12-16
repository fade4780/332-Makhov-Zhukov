package com.restaurant.model;

public class Dish {
    private int id;
    private String name;
    private boolean vegetarian;
    private int calories;
    private DishType type;
    private double basePrice;
    private boolean isBusinessLunch;
    private boolean isMorningMenu;
    private boolean isChefSpecial;

    public Dish() {
    }

    public Dish(String name, boolean vegetarian, int calories, DishType type, 
                double basePrice, boolean isBusinessLunch, boolean isMorningMenu, boolean isChefSpecial) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
        this.basePrice = basePrice;
        this.isBusinessLunch = isBusinessLunch;
        this.isMorningMenu = isMorningMenu;
        this.isChefSpecial = isChefSpecial;
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

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public DishType getType() {
        return type;
    }

    public void setType(DishType type) {
        this.type = type;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public boolean isBusinessLunch() {
        return isBusinessLunch;
    }

    public void setBusinessLunch(boolean businessLunch) {
        isBusinessLunch = businessLunch;
    }

    public boolean isMorningMenu() {
        return isMorningMenu;
    }

    public void setMorningMenu(boolean morningMenu) {
        isMorningMenu = morningMenu;
    }

    public boolean isChefSpecial() {
        return isChefSpecial;
    }

    public void setChefSpecial(boolean chefSpecial) {
        isChefSpecial = chefSpecial;
    }

    public double getPrice(City city) {
        return basePrice * city.getPriceMultiplier();
    }
}

