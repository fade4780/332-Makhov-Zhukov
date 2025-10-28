package bank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public abstract class Account {
    private String fullName;
    private String accountNumber;
    protected BigDecimal balance;
    private boolean bonusGiven = false; // приветственный бонус

    public Account(String fullName, BigDecimal initialDeposit) {
        if (fullName == null || fullName.isBlank())
            throw new IllegalArgumentException("Имя клиента не может быть пустым!");
        if (initialDeposit.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Начальная сумма не может быть отрицательной!");

        this.fullName = fullName;
        this.accountNumber = generateAccountNumber();
        this.balance = initialDeposit.setScale(2, RoundingMode.HALF_UP);
    }

    private String generateAccountNumber() {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }

    public String getFullName() { return fullName; }
    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getBalance() { return balance; }

    // Вывод баланса
    public void printBalance() {
        System.out.printf("💳 %s (№%s): %.2f ₽%n", fullName, accountNumber, balance);
    }

    // Перевод на другой счёт
    public void transferTo(Account other, BigDecimal amount) {
        if (other == null) throw new IllegalArgumentException("Целевой счёт не может быть null!");
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Сумма перевода должна быть положительной!");
        if (balance.compareTo(amount) < 0)
            throw new IllegalArgumentException("Недостаточно средств для перевода!");

        balance = balance.subtract(amount);
        other.balance = other.balance.add(amount);
        System.out.printf("➡️ Переведено %.2f ₽ от %s к %s%n", amount, fullName, other.fullName);
    }

    // Оплата услуги (с расчётом кешбэка и приветственного бонуса)
    public void payForService(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Сумма оплаты должна быть положительной!");
        if (balance.compareTo(amount) < 0)
            throw new IllegalArgumentException("Недостаточно средств для оплаты!");

        // Вычисляем кешбэк в зависимости от типа клиента
        BigDecimal cashback = calculateCashback(amount);

        balance = balance.subtract(amount).add(cashback);

        System.out.printf("🧾 %s оплатил услугу на %.2f ₽, кешбэк: %.2f ₽%n",
                fullName, amount, cashback);

        // После первой оплаты начисляем бонус
        if (!bonusGiven) {
            giveWelcomeBonus();
        }
    }

    private void giveWelcomeBonus() {
        balance = balance.add(BigDecimal.valueOf(1000));
        bonusGiven = true;
        System.out.printf("🎁 %s получил приветственный бонус 1000 ₽!%n", fullName);
    }

    // Абстрактные методы для разных типов клиентов
    protected abstract BigDecimal calculateCashback(BigDecimal amount);
    public abstract void monthlyFee();

    @Override
    public String toString() {
        return String.format("%s (%s) — баланс: %.2f ₽", fullName, getClass().getSimpleName(), balance);
    }
}
