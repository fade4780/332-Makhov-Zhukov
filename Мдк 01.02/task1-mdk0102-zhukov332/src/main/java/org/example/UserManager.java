package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserManager {
    private final List<User> users = new ArrayList<>();

    public void addUser(String name, int age, String gender) {
        users.add(new User(name, age, gender));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void printAllUsers() {
        users.forEach(System.out::println);
    }

    public List<User> getMaleUsers() {
        return users.stream()
                .filter(u -> u.getGender().equals("мужской"))
                .collect(Collectors.toList());
    }

    public List<User> getFemaleUsers() {
        return users.stream()
                .filter(u -> u.getGender().equals("женский"))
                .collect(Collectors.toList());
    }

    public int getTotalUsers() {
        return users.size();
    }

    public double getAverageAge() {
        if (users.isEmpty()) return 0;
        return users.stream()
                .mapToInt(User::getAge)
                .average()
                .orElse(0);
    }

    public static void main(String[] args) {
        UserManager manager = new UserManager();
        manager.addUser("Егор", 18, "мужской");
        manager.addUser("Анна", 22, "женский");
        manager.addUser("Иван", 25, "мужской");

        System.out.println("Все пользователи:");
        manager.printAllUsers();
        System.out.println("\nМужчины:");
        manager.getMaleUsers().forEach(System.out::println);
        System.out.println("\nЖенщины:");
        manager.getFemaleUsers().forEach(System.out::println);
        System.out.println("\nОбщее количество: " + manager.getTotalUsers());
        System.out.println("Средний возраст: " + manager.getAverageAge());
    }
}
