package bank;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BaseClient extends Account {
    public BaseClient(String fullName, BigDecimal initialDeposit) {
        super(fullName, initialDeposit);
    }

    @Override
    protected BigDecimal calculateCashback(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(10_000)) >= 0)
            return amount.multiply(BigDecimal.valueOf(0.01)).setScale(2, RoundingMode.HALF_UP);
        return BigDecimal.ZERO;
    }

    @Override
    public void monthlyFee() {
        balance = balance.subtract(BigDecimal.valueOf(100));
        if (balance.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalStateException("⚠️ Отрицательный баланс у " + getFullName());
        System.out.printf("💸 Списана ежемесячная плата (100 ₽) для %s%n", getFullName());
    }
}
