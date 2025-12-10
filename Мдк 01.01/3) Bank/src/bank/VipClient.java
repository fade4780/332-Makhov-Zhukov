package bank;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class VipClient extends Account {
    public VipClient(String fullName, BigDecimal initialDeposit) {
        super(fullName, initialDeposit);
    }

    @Override
    protected BigDecimal calculateCashback(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(100_000)) >= 0)
            return amount.multiply(BigDecimal.valueOf(0.10)).setScale(2, RoundingMode.HALF_UP);
        else if (amount.compareTo(BigDecimal.valueOf(10_000)) >= 0)
            return amount.multiply(BigDecimal.valueOf(0.05)).setScale(2, RoundingMode.HALF_UP);
        else
            return amount.multiply(BigDecimal.valueOf(0.01)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void monthlyFee() {
        System.out.printf("👑 %s — без ежемесячной платы (ВИП)%n", getFullName());
    }
}
