package org.example;

import java.util.ArrayList;
import java.util.List;

public class AuthManager {
    private final List<UserData> users = new ArrayList<>();

    private static class UserData {
        String login;
        String password;

        UserData(String login, String password) {
            this.login = login;
            this.password = password;
        }

        @Override
        public String toString() {
            return "Login: " + login + ", Password: " + password;
        }
    }

    public void register(String login, String password, String confirmPassword) throws IllegalArgumentException {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Ошибка: пароли не совпадают!");
        }

        if (!login.matches("[A-Za-z0-9_]+")) {
            throw new IllegalArgumentException("Ошибка: логин содержит недопустимые символы!");
        }
        if (login.length() >= 15) {
            throw new IllegalArgumentException("Ошибка: логин должен быть короче 15 символов!");
        }

        if (!password.matches("[A-Za-z0-9_]+")) {
            throw new IllegalArgumentException("Ошибка: пароль содержит недопустимые символы!");
        }
        if (password.length() < 7 || password.length() > 20) {
            throw new IllegalArgumentException("Ошибка: длина пароля должна быть от 7 до 20 символов!");
        }

        users.add(new UserData(login, password));
        System.out.println("✅ Пользователь " + login + " успешно зарегистрирован!");
    }

    public void printAllUsers() {
        if (users.isEmpty()) {
            System.out.println("Нет зарегистрированных пользователей.");
            return;
        }
        System.out.println("Список сохранённых логинов и паролей:");
        for (UserData user : users) {
            System.out.println(user);
        }
    }

    public int getUserCount() {
        return users.size();
    }
}
