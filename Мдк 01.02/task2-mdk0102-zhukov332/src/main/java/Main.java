package org.example;

public class Main {
    public static void main(String[] args) {
        AuthManager auth = new AuthManager();

        try {
            auth.register("egor332", "mypassword_1", "mypassword_1");
            auth.register("userTest", "pass_123", "pass_123");
            // auth.register("bad login", "short", "short"); // пример ошибки
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        auth.printAllUsers();
        System.out.println("Всего пользователей: " + auth.getUserCount());
    }
}
