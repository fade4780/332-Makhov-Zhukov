package com.restaurant.util;

import com.restaurant.dao.*;
import com.restaurant.model.*;

import java.sql.SQLException;

public class DatabaseInitializer {
    private DatabaseConnection dbConnection;
    private DishDAO dishDAO;
    private DrinkDAO drinkDAO;
    private RestaurantDAO restaurantDAO;
    private OrderDAO orderDAO;

    public DatabaseInitializer(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.dishDAO = new DishDAO(dbConnection);
        this.drinkDAO = new DrinkDAO(dbConnection);
        this.restaurantDAO = new RestaurantDAO(dbConnection);
        this.orderDAO = new OrderDAO(dbConnection);
    }

    public void initialize() throws SQLException {
        dishDAO.createTable();
        drinkDAO.createTable();
        restaurantDAO.createTable();
        orderDAO.createTable();

        if (isDatabaseEmpty()) {
            insertInitialData();
        }
    }

    private boolean isDatabaseEmpty() throws SQLException {
        try (var conn = dbConnection.getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT COUNT(*) FROM restaurants")) {
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        }
        return true;
    }

    private void insertInitialData() throws SQLException {
        Dish dish1 = new Dish("Стейк", false, 450, DishType.MEAT, 800.0, false, false, false);
        dish1.setId(1);
        Dish dish2 = new Dish("Салат Цезарь", false, 250, DishType.OTHER, 350.0, true, false, false);
        dish2.setId(2);
        Dish dish3 = new Dish("Лосось на гриле", false, 320, DishType.FISH, 650.0, false, false, false);
        dish3.setId(3);
        Dish dish4 = new Dish("Омлет", false, 200, DishType.OTHER, 250.0, false, true, false);
        dish4.setId(4);
        Dish dish5 = new Dish("Борщ", true, 180, DishType.OTHER, 280.0, true, false, false);
        dish5.setId(5);
        Dish dish6 = new Dish("Вегетарианская паста", true, 300, DishType.OTHER, 400.0, true, false, false);
        dish6.setId(6);
        
        Dish chefDish1 = new Dish("Фирменный стейк от шефа", false, 500, DishType.MEAT, 1200.0, false, false, true);
        chefDish1.setId(7);
        Dish chefDish2 = new Dish("Особый лосось от шефа", false, 380, DishType.FISH, 950.0, false, false, true);
        chefDish2.setId(8);
        Dish chefDish3 = new Dish("Эксклюзивное блюдо от шефа", false, 420, DishType.MEAT, 1100.0, false, false, true);
        chefDish3.setId(9);
        Dish chefDish4 = new Dish("Авторское блюдо от шефа", false, 350, DishType.FISH, 900.0, false, false, true);
        chefDish4.setId(10);
        Dish chefDish5 = new Dish("Уникальное блюдо от шефа", false, 400, DishType.MEAT, 1050.0, false, false, true);
        chefDish5.setId(11);

        dishDAO.insertWithId(dish1);
        dishDAO.insertWithId(dish2);
        dishDAO.insertWithId(dish3);
        dishDAO.insertWithId(dish4);
        dishDAO.insertWithId(dish5);
        dishDAO.insertWithId(dish6);
        dishDAO.insertWithId(chefDish1);
        dishDAO.insertWithId(chefDish2);
        dishDAO.insertWithId(chefDish3);
        dishDAO.insertWithId(chefDish4);
        dishDAO.insertWithId(chefDish5);

        Drink drink1 = new Drink("Кола", 150, false, 150.0);
        drink1.setId(1);
        Drink drink2 = new Drink("Сок апельсиновый", 120, false, 180.0);
        drink2.setId(2);
        Drink drink3 = new Drink("Вино красное", 200, true, 450.0);
        drink3.setId(3);
        Drink drink4 = new Drink("Пиво", 180, true, 250.0);
        drink4.setId(4);
        Drink drink5 = new Drink("Кофе", 50, false, 200.0);
        drink5.setId(5);
        Drink drink6 = new Drink("Чай", 20, false, 150.0);
        drink6.setId(6);

        drinkDAO.insertWithId(drink1);
        drinkDAO.insertWithId(drink2);
        drinkDAO.insertWithId(drink3);
        drinkDAO.insertWithId(drink4);
        drinkDAO.insertWithId(drink5);
        drinkDAO.insertWithId(drink6);

        Restaurant rest1 = new Restaurant("Ресторан Москва-1", City.MOSCOW, 7);
        rest1.setId(1);
        Restaurant rest2 = new Restaurant("Ресторан Москва-2", City.MOSCOW, 8);
        rest2.setId(2);
        Restaurant rest3 = new Restaurant("Ресторан Москва-3", City.MOSCOW, 9);
        rest3.setId(3);
        
        Restaurant rest4 = new Restaurant("Ресторан СПб-1", City.SAINT_PETERSBURG, 10);
        rest4.setId(4);
        Restaurant rest5 = new Restaurant("Ресторан СПб-2", City.SAINT_PETERSBURG, 11);
        rest5.setId(5);

        restaurantDAO.insertWithId(rest1);
        restaurantDAO.insertWithId(rest2);
        restaurantDAO.insertWithId(rest3);
        restaurantDAO.insertWithId(rest4);
        restaurantDAO.insertWithId(rest5);
    }
}

