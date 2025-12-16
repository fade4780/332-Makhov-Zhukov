package com.restaurant.service;

import com.restaurant.dao.DishDAO;
import com.restaurant.dao.DrinkDAO;
import com.restaurant.dao.RestaurantDAO;
import com.restaurant.model.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class MenuService {
    private DishDAO dishDAO;
    private DrinkDAO drinkDAO;
    private RestaurantDAO restaurantDAO;
    private PromotionService promotionService;

    public MenuService(DishDAO dishDAO, DrinkDAO drinkDAO, RestaurantDAO restaurantDAO, PromotionService promotionService) {
        this.dishDAO = dishDAO;
        this.drinkDAO = drinkDAO;
        this.restaurantDAO = restaurantDAO;
        this.promotionService = promotionService;
    }

    public Menu getMenuForRestaurant(int restaurantId, LocalDateTime currentTime) {
        try {
            Restaurant restaurant = restaurantDAO.findById(restaurantId);
            if (restaurant == null) {
                return null;
            }

            List<Dish> allDishes = dishDAO.findAll();
            List<Drink> allDrinks = drinkDAO.findAll();

            LocalTime time = currentTime.toLocalTime();
            int hour = time.getHour();
            boolean isWeekday = currentTime.getDayOfWeek().getValue() < 6;

            List<Dish> availableDishes = allDishes.stream()
                    .filter(dish -> {
                        if (dish.isChefSpecial()) {
                            return dish.getId() == restaurant.getChefSpecialDishId();
                        }
                        if (dish.isBusinessLunch()) {
                            return hour >= 12 && hour < 15;
                        }
                        if (dish.isMorningMenu()) {
                            return hour >= 7 && hour < 11;
                        }
                        return true;
                    })
                    .collect(Collectors.toList());

            Menu menu = new Menu(restaurant, availableDishes, allDrinks);
            menu.setCurrentTime(currentTime);
            menu.setPromotionService(promotionService);
            return menu;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка получения меню", e);
        }
    }

    public void updateDishPrice(int dishId, double newPrice) {
        try {
            dishDAO.updatePrice(dishId, newPrice);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка обновления цены блюда", e);
        }
    }

    public void updateDrinkPrice(int drinkId, double newPrice) {
        try {
            drinkDAO.updatePrice(drinkId, newPrice);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка обновления цены напитка", e);
        }
    }
}

