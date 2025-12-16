package com.restaurant.model;

public enum City {
    MOSCOW("Москва", 1.1),
    SAINT_PETERSBURG("Санкт-Петербург", 1.0);

    private final String displayName;
    private final double priceMultiplier;

    City(String displayName, double priceMultiplier) {
        this.displayName = displayName;
        this.priceMultiplier = priceMultiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }
}

