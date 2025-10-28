package bank;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PremiumClient extends Account {
    public PremiumClient(String fullName, BigDecimal initialDeposit) {
        super(fullName, initialDeposit);
    }

    @Override
    protected BigDecimal calculateCashback(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(10_000)) >= 0)
            return amount.multiply(BigDecimal.valueOf(0.05)).setScale(2, RoundingMode.HALF_UP);
        return BigDecimal.ZERO;
    }

    @Override
    public void monthlyFee() {
        System.out.printf("✅ %s — без ежемесячной платы (Премиум)%n", getFullName());
    }
}
