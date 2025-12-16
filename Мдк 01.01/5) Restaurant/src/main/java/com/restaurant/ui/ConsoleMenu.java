package com.restaurant.ui;

import com.restaurant.model.*;
import com.restaurant.service.MenuService;
import com.restaurant.service.OrderService;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private Scanner scanner;
    private MenuService menuService;
    private OrderService orderService;
    private List<Restaurant> restaurants;
    private LocalDateTime currentTime;

    public ConsoleMenu(Scanner scanner, MenuService menuService, OrderService orderService, 
                      List<Restaurant> restaurants, LocalDateTime currentTime) {
        this.scanner = scanner;
        this.menuService = menuService;
        this.orderService = orderService;
        this.restaurants = restaurants;
        this.currentTime = currentTime;
    }

    public void run() {
        while (true) {
            showMainMenu();
            int choice = readInt();
            
            switch (choice) {
                case 1:
                    selectRestaurant();
                    break;
                case 2:
                    changePrices();
                    break;
                case 3:
                    setTime();
                    break;
                case 0:
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println("Текущее время: " + currentTime.toLocalTime().format(timeFormatter));
        System.out.println("1. Выбрать ресторан и сделать заказ");
        System.out.println("2. Изменить цены");
        System.out.println("3. Установить время");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void selectRestaurant() {
        while (true) {
            System.out.println("\n=== ВЫБОР РЕСТОРАНА ===");
            restaurants.forEach(r -> 
                System.out.printf("%d. %s (%s)%n", r.getId(), r.getName(), r.getCity().getDisplayName())
            );
            System.out.println("0. Назад");
            System.out.print("Выберите ресторан: ");
            int restaurantId = readInt();
            
            if (restaurantId == 0) {
                return;
            }
            
            Restaurant selectedRestaurant = restaurants.stream()
                    .filter(r -> r.getId() == restaurantId)
                    .findFirst()
                    .orElse(null);
            
            if (selectedRestaurant == null) {
                System.out.println("Ресторан не найден!");
                continue;
            }

            Menu menu = menuService.getMenuForRestaurant(restaurantId, currentTime);
            if (menu == null) {
                System.out.println("Ошибка загрузки меню!");
                continue;
            }

            menu.printMenu();
            makeOrder(restaurantId, menu);
        }
    }

    private void makeOrder(int restaurantId, Menu menu) {
        Order order = orderService.createOrder(restaurantId, currentTime);
        
        while (true) {
            System.out.println("\n=== ЗАКАЗ ===");
            System.out.println("1. Добавить блюдо");
            System.out.println("2. Добавить напиток");
            System.out.println("3. Показать текущий заказ");
            System.out.println("4. Завершить заказ");
            System.out.println("0. Назад (отменить заказ)");
            System.out.print("Выберите действие: ");
            
            int choice = readInt();
            
            switch (choice) {
                case 1:
                    addDishToOrder(order, menu);
                    break;
                case 2:
                    addDrinkToOrder(order, menu);
                    break;
                case 3:
                    showCurrentOrder(order);
                    break;
                case 4:
                    completeOrder(order);
                    return;
                case 0:
                    System.out.println("Заказ отменен.");
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private void addDishToOrder(Order order, Menu menu) {
        System.out.println("\n--- ВЫБОР БЛЮДА ---");
        menu.getDishes().forEach(dish -> 
            System.out.printf("%d. %s - %.2f руб.%n", 
                dish.getId(), dish.getName(), dish.getPrice(order.getRestaurant().getCity()))
        );
        System.out.println("0. Назад");
        System.out.print("Выберите блюдо: ");
        int dishId = readInt();
        
        if (dishId == 0) {
            return;
        }
        
        System.out.print("Количество: ");
        int quantity = readInt();
        
        if (quantity > 0) {
            try {
                orderService.addDishToOrder(order, dishId, quantity);
                System.out.println("Блюдо добавлено в заказ!");
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        } else {
            System.out.println("Количество должно быть больше 0!");
        }
    }

    private void addDrinkToOrder(Order order, Menu menu) {
        System.out.println("\n--- ВЫБОР НАПИТКА ---");
        menu.getDrinks().forEach(drink -> 
            System.out.printf("%d. %s - %.2f руб.%n", 
                drink.getId(), drink.getName(), drink.getPrice(order.getRestaurant().getCity()))
        );
        System.out.println("0. Назад");
        System.out.print("Выберите напиток: ");
        int drinkId = readInt();
        
        if (drinkId == 0) {
            return;
        }
        
        System.out.print("Количество: ");
        int quantity = readInt();
        
        if (quantity > 0) {
            try {
                orderService.addDrinkToOrder(order, drinkId, quantity);
                System.out.println("Напиток добавлен в заказ!");
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        } else {
            System.out.println("Количество должно быть больше 0!");
        }
    }

    private void showCurrentOrder(Order order) {
        if (order.getItems().isEmpty()) {
            System.out.println("Заказ пуст.");
            return;
        }
        
        System.out.println("\n--- ТЕКУЩИЙ ЗАКАЗ ---");
        order.getItems().forEach(item -> {
            if (item.isDish()) {
                System.out.printf("%s x%d - %.2f руб.%n",
                        item.getDish().getName(), item.getQuantity(), item.getTotal());
            } else {
                System.out.printf("%s x%d - %.2f руб.%n",
                        item.getDrink().getName(), item.getQuantity(), item.getTotal());
            }
        });
        System.out.println();
    }

    private void completeOrder(Order order) {
        if (order.getItems().isEmpty()) {
            System.out.println("Заказ пуст. Добавьте позиции в заказ.");
            return;
        }
        
        orderService.calculateTotal(order, currentTime);
        orderService.printReceipt(order, currentTime);
        orderService.saveOrder(order);
        System.out.println("Заказ сохранен!");
    }

    private void changePrices() {
        while (true) {
            System.out.println("\n=== ИЗМЕНЕНИЕ ЦЕН ===");
            System.out.println("1. Изменить цену блюда");
            System.out.println("2. Изменить цену напитка");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");
            
            int choice = readInt();
            
            if (choice == 0) {
                return;
            } else if (choice == 1) {
                System.out.print("Введите ID блюда: ");
                int dishId = readInt();
                System.out.print("Введите новую базовую цену: ");
                double newPrice = readDouble();
                try {
                    menuService.updateDishPrice(dishId, newPrice);
                    System.out.println("Цена обновлена!");
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            } else if (choice == 2) {
                System.out.print("Введите ID напитка: ");
                int drinkId = readInt();
                System.out.print("Введите новую базовую цену: ");
                double newPrice = readDouble();
                try {
                    menuService.updateDrinkPrice(drinkId, newPrice);
                    System.out.println("Цена обновлена!");
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            } else {
                System.out.println("Неверный выбор.");
            }
        }
    }

    private void setTime() {
        while (true) {
            System.out.println("\n=== УСТАНОВКА ВРЕМЕНИ ===");
            System.out.println("1. Установить время");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");
            
            int choice = readInt();
            
            if (choice == 0) {
                return;
            } else if (choice == 1) {
                System.out.print("Введите час (0-23): ");
                int hour = readInt();
                System.out.print("Введите минуту (0-59): ");
                int minute = readInt();
                System.out.print("Введите день недели (1-7, где 1=понедельник): ");
                int dayOfWeek = readInt();
                
                if (hour >= 0 && hour < 24 && minute >= 0 && minute < 60 && dayOfWeek >= 1 && dayOfWeek <= 7) {
                    currentTime = LocalDateTime.now()
                            .withHour(hour)
                            .withMinute(minute)
                            .with(DayOfWeek.of(dayOfWeek));
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                    System.out.println("Время установлено: " + currentTime.toLocalTime().format(timeFormatter));
                } else {
                    System.out.println("Неверные значения!");
                }
            } else {
                System.out.println("Неверный выбор.");
            }
        }
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число: ");
            }
        }
    }

    private double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число: ");
            }
        }
    }
}

