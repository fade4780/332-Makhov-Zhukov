package com.restaurant.service;

import com.restaurant.dao.DishDAO;
import com.restaurant.dao.DrinkDAO;
import com.restaurant.dao.OrderDAO;
import com.restaurant.dao.RestaurantDAO;
import com.restaurant.model.*;

import java.time.LocalDateTime;
import java.util.List;

public class OrderService {
    private OrderDAO orderDAO;
    private DishDAO dishDAO;
    private DrinkDAO drinkDAO;
    private RestaurantDAO restaurantDAO;
    private PromotionService promotionService;

    public OrderService(OrderDAO orderDAO, DishDAO dishDAO, DrinkDAO drinkDAO, 
                       RestaurantDAO restaurantDAO, PromotionService promotionService) {
        this.orderDAO = orderDAO;
        this.dishDAO = dishDAO;
        this.drinkDAO = drinkDAO;
        this.restaurantDAO = restaurantDAO;
        this.promotionService = promotionService;
    }

    public Order createOrder(int restaurantId, LocalDateTime currentTime) {
        try {
            Restaurant restaurant = restaurantDAO.findById(restaurantId);
            if (restaurant == null) {
                throw new IllegalArgumentException("Ресторан не найден");
            }
            return new Order(restaurant);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания заказа", e);
        }
    }

    public void addDishToOrder(Order order, int dishId, int quantity) {
        try {
            Dish dish = dishDAO.findById(dishId);
            if (dish == null) {
                throw new IllegalArgumentException("Блюдо не найдено");
            }
            double price = dish.getPrice(order.getRestaurant().getCity());
            OrderItem item = new OrderItem(dish, quantity, price);
            order.addItem(item);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка добавления блюда в заказ", e);
        }
    }

    public void addDrinkToOrder(Order order, int drinkId, int quantity) {
        try {
            Drink drink = drinkDAO.findById(drinkId);
            if (drink == null) {
                throw new IllegalArgumentException("Напиток не найден");
            }
            double price = drink.getPrice(order.getRestaurant().getCity());
            OrderItem item = new OrderItem(drink, quantity, price);
            order.addItem(item);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка добавления напитка в заказ", e);
        }
    }

    public void calculateTotal(Order order, LocalDateTime currentTime) {
        double subtotal = order.calculateSubtotal();
        double discount = promotionService.calculateDiscount(order.getItems(), currentTime);
        order.setDiscountAmount(discount);
        order.setTotalAmount(subtotal - discount);
    }

    public void saveOrder(Order order) {
        try {
            orderDAO.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка сохранения заказа", e);
        }
    }

    public void printReceipt(Order order, LocalDateTime currentTime) {
        System.out.println("\n=== ЧЕК ===");
        System.out.println("Ресторан: " + order.getRestaurant().getName());
        System.out.println("Город: " + order.getRestaurant().getCity().getDisplayName());
        System.out.println("Время заказа: " + currentTime);
        System.out.println("\n--- ЗАКАЗ ---");
        
        order.getItems().forEach(item -> {
            if (item.isDish()) {
                System.out.printf("%s x%d - %.2f руб. (итого: %.2f руб.)%n",
                        item.getDish().getName(), item.getQuantity(), 
                        item.getPrice(), item.getTotal());
            } else {
                System.out.printf("%s x%d - %.2f руб. (итого: %.2f руб.)%n",
                        item.getDrink().getName(), item.getQuantity(),
                        item.getPrice(), item.getTotal());
            }
        });

        double subtotal = order.calculateSubtotal();
        System.out.printf("\nПодытог: %.2f руб.%n", subtotal);
        
        if (order.getDiscountAmount() > 0) {
            System.out.printf("Скидка (20%%): -%.2f руб.%n", order.getDiscountAmount());
        }
        
        System.out.printf("ИТОГО: %.2f руб.%n", order.getTotalAmount());
        System.out.println("================\n");
    }
}

