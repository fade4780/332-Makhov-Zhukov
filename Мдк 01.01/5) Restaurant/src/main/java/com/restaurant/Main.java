package com.restaurant;

import com.restaurant.dao.DatabaseConnection;
import com.restaurant.dao.DishDAO;
import com.restaurant.dao.DrinkDAO;
import com.restaurant.dao.OrderDAO;
import com.restaurant.dao.RestaurantDAO;
import com.restaurant.model.Restaurant;
import com.restaurant.service.MenuService;
import com.restaurant.service.OrderService;
import com.restaurant.service.PromotionService;
import com.restaurant.ui.ConsoleMenu;
import com.restaurant.util.DatabaseInitializer;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        
        try {
            DatabaseInitializer initializer = new DatabaseInitializer(dbConnection);
            initializer.initialize();
            System.out.println("База данных инициализирована.");

            DishDAO dishDAO = new DishDAO(dbConnection);
            DrinkDAO drinkDAO = new DrinkDAO(dbConnection);
            RestaurantDAO restaurantDAO = new RestaurantDAO(dbConnection);
            OrderDAO orderDAO = new OrderDAO(dbConnection);

            PromotionService promotionService = new PromotionService();
            MenuService menuService = new MenuService(dishDAO, drinkDAO, restaurantDAO, promotionService);
            OrderService orderService = new OrderService(orderDAO, dishDAO, drinkDAO, restaurantDAO, promotionService);

            List<Restaurant> restaurants = restaurantDAO.findAll();

            Scanner scanner = new Scanner(System.in);
            LocalDateTime currentTime = LocalDateTime.now();
            ConsoleMenu consoleMenu = new ConsoleMenu(scanner, menuService, orderService, restaurants, currentTime);

            consoleMenu.run();

        } catch (SQLException e) {
            System.err.println("Ошибка работы с базой данных: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }
    }
}

