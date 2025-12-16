package com.restaurant.model;

public enum DishType {
    MEAT("Мясо"),
    FISH("Рыба"),
    OTHER("Другое");

    private final String displayName;

    DishType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

