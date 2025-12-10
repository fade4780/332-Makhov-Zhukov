package fitness;

import java.time.LocalTime;

public class Fitness {
    private static final int MAX_GYM = 50;
    private static final int MAX_POOL = 20;
    private static final int MAX_GROUP = 10;

    private Membership[] gymZone = new Membership[MAX_GYM];
    private Membership[] poolZone = new Membership[MAX_POOL];
    private Membership[] groupZone = new Membership[MAX_GROUP];

    public void enterZone(Membership m, Zone zone) {
        if (m == null) {
            System.out.println("❌ Ошибка: абонемент отсутствует!");
            return;
        }

        int hour = LocalTime.now().getHour();

        if (!m.isValid()) {
            System.out.println("⛔ Абонемент " + m.getOwner().getFullName() + " недействителен!");
            return;
        }

        if (!m.canAccessZone(zone, hour)) {
            System.out.println("⛔ Доступ в " + zoneToString(zone) + " запрещён по типу абонемента или времени!");
            return;
        }

        switch (zone) {
            case GYM -> addToZone(m, gymZone, "тренажерный зал");
            case POOL -> addToZone(m, poolZone, "бассейн");
            case GROUP -> addToZone(m, groupZone, "групповые занятия");
        }
    }

    private void addToZone(Membership m, Membership[] zoneArr, String zoneName) {
        for (int i = 0; i < zoneArr.length; i++) {
            if (zoneArr[i] == null) {
                zoneArr[i] = m;
                System.out.println("✅ " + m.getOwner().getFullName() + " вошёл в " + zoneName);
                return;
            }
        }
        System.out.println("❌ Места в зоне \"" + zoneName + "\" закончились!");
    }

    public void closeClub() {
        clearZone(gymZone, "тренажерный зал");
        clearZone(poolZone, "бассейн");
        clearZone(groupZone, "групповые занятия");
    }

    private void clearZone(Membership[] zoneArr, String name) {
        for (int i = 0; i < zoneArr.length; i++) {
            zoneArr[i] = null;
        }
        System.out.println("🏁 " + name + " очищен, все клиенты покинули клуб.");
    }

    private String zoneToString(Zone z) {
        return switch (z) {
            case GYM -> "тренажерный зал";
            case POOL -> "бассейн";
            case GROUP -> "групповые занятия";
        };
    }
}
