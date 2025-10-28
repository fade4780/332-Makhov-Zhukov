package bank;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankAppUI {
    private final Map<String, Account> accounts = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public BankAppUI() {
        // Создаём несколько клиентов заранее
        accounts.put("1001", new BaseClient("Иван Петров", BigDecimal.valueOf(5000)));
        accounts.put("1002", new PremiumClient("Мария Иванова", BigDecimal.valueOf(20000)));
        accounts.put("1003", new VipClient("Сергей Сидоров", BigDecimal.valueOf(100000)));
    }

    public void start() {
        System.out.println("🏦 Добро пожаловать в консольное банковское приложение!\n");

        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1️⃣ Войти в аккаунт");
            System.out.println("2️⃣ Просмотреть список клиентов");
            System.out.println("0️⃣ Выйти");
            System.out.print("> ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> loginMenu();
                case "2" -> showAllAccounts();
                case "0" -> {
                    System.out.println("👋 До свидания!");
                    return;
                }
                default -> System.out.println("⚠️ Неверный выбор, попробуйте снова.\n");
            }
        }
    }

    private void showAllAccounts() {
        System.out.println("\n📋 Список клиентов:");
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            System.out.println("ID: " + entry.getKey() + " — " + entry.getValue());
        }
        System.out.println();
    }

    private void loginMenu() {
        System.out.print("Введите ID клиента: ");
        String id = scanner.nextLine().trim();

        Account acc = accounts.get(id);
        if (acc == null) {
            System.out.println("❌ Клиент с таким ID не найден.\n");
            return;
        }

        System.out.printf("✅ Добро пожаловать, %s!%n%n", acc.getFullName());
        accountMenu(acc);
    }

    private void accountMenu(Account acc) {
        while (true) {
            System.out.println("Меню клиента:");
            System.out.println("1️⃣ Проверить баланс");
            System.out.println("2️⃣ Оплатить услугу");
            System.out.println("3️⃣ Перевести деньги другому клиенту");
            System.out.println("4️⃣ Списать ежемесячную плату");
            System.out.println("0️⃣ Выйти в главное меню");
            System.out.print("> ");

            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> acc.printBalance();
                    case "2" -> payService(acc);
                    case "3" -> transferMoney(acc);
                    case "4" -> acc.monthlyFee();
                    case "0" -> {
                        System.out.println();
                        return;
                    }
                    default -> System.out.println("⚠️ Неверный выбор, попробуйте снова.\n");
                }
            } catch (Exception e) {
                System.out.println("❌ Ошибка: " + e.getMessage() + "\n");
            }
        }
    }

    private void payService(Account acc) {
        System.out.print("Введите сумму оплаты: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine().trim());
        acc.payForService(amount);
    }

    private void transferMoney(Account acc) {
        System.out.print("Введите ID получателя: ");
        String toId = scanner.nextLine().trim();
        Account receiver = accounts.get(toId);
        if (receiver == null) {
            System.out.println("❌ Получатель не найден.\n");
            return;
        }

        System.out.print("Введите сумму перевода: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine().trim());
        acc.transferTo(receiver, amount);
    }
}
