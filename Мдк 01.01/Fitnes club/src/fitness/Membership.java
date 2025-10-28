package fitness;

import java.time.LocalDate;

public class Membership {
    private Member owner;
    private MembershipType type;
    private LocalDate regDate;
    private LocalDate endDate;

    public Membership(Member owner, MembershipType type, LocalDate endDate) {
        if (owner == null || type == null || endDate == null)
            throw new IllegalArgumentException("Все поля обязательны!");
        this.owner = owner;
        this.type = type;
        this.regDate = LocalDate.now();
        this.endDate = endDate;
    }

    public Member getOwner() { return owner; }
    public MembershipType getType() { return type; }
    public LocalDate getRegDate() { return regDate; }
    public LocalDate getEndDate() { return endDate; }

    public boolean isValid() {
        LocalDate today = LocalDate.now();
        return !today.isAfter(endDate);
    }

    public boolean canAccessZone(Zone zone, int hour) {
        if (!isValid()) return false;

        switch (type) {
            case ONE_TIME:
                if (hour < 8 || hour > 22) return false;
                return zone == Zone.GYM || zone == Zone.POOL;
            case DAYTIME:
                if (hour < 8 || hour >= 16) return false;
                return zone == Zone.GYM || zone == Zone.GROUP;
            case FULL:
                return hour >= 8 && hour <= 22;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return owner + " — " + type + " (до " + endDate + ")";
    }
}
