package com.restaurant.model;

import com.restaurant.service.PromotionService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Menu {
    private Restaurant restaurant;
    private List<Dish> dishes;
    private List<Drink> drinks;
    private LocalDateTime currentTime;
    private PromotionService promotionService;

    public Menu(Restaurant restaurant, List<Dish> dishes, List<Drink> drinks) {
        this.restaurant = restaurant;
        this.dishes = dishes;
        this.drinks = drinks;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public PromotionService getPromotionService() {
        return promotionService;
    }

    public void setPromotionService(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    public void printMenu() {
        System.out.println("\n=== МЕНЮ: " + restaurant.getName() + " ===");
        System.out.println("Город: " + restaurant.getCity().getDisplayName());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println("Время: " + currentTime.toLocalTime().format(timeFormatter));
        
        if (promotionService != null && promotionService.isPromotionActive(currentTime)) {
            System.out.println("🎉 АКЦИЯ: Скидка 20% на еду до 18:00!");
        }

        System.out.println("\n--- БЛЮДА ---");
        dishes.forEach(dish -> {
            double price = dish.getPrice(restaurant.getCity());
            String special = "";
            if (dish.isChefSpecial()) {
                special = " [БЛЮДО ОТ ШЕФА]";
            }
            if (dish.isBusinessLunch()) {
                special += " [БИЗНЕС-ЛАНЧ]";
            }
            if (dish.isMorningMenu()) {
                special += " [УТРЕННЕЕ МЕНЮ]";
            }
            System.out.printf("%d. %s - %.2f руб. (Калории: %d, Тип: %s%s)%n",
                    dish.getId(), dish.getName(), price, dish.getCalories(),
                    dish.getType().getDisplayName(), special);
        });

        System.out.println("\n--- НАПИТКИ ---");
        drinks.forEach(drink -> {
            double price = drink.getPrice(restaurant.getCity());
            System.out.printf("%d. %s - %.2f руб. (Калории: %d, %s)%n",
                    drink.getId(), drink.getName(), price, drink.getCalories(),
                    drink.isAlcoholic() ? "Алкогольный" : "Безалкогольный");
        });
        System.out.println();
    }
}

