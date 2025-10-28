package fitness;

import java.time.LocalDate;

public class Application {
    public static void main(String[] args) {
        Fitness fitnessClub = new Fitness();

        Member ivan = new Member("Иван", "Петров", 1990);
        Member olga = new Member("Ольга", "Иванова", 1995);
        Member sergey = new Member("Сергей", "Сидоров", 1985);

        Membership oneTime = new Membership(ivan, MembershipType.ONE_TIME, LocalDate.now().plusDays(1));
        Membership day = new Membership(olga, MembershipType.DAYTIME, LocalDate.now().plusDays(5));
        Membership full = new Membership(sergey, MembershipType.FULL, LocalDate.now().plusDays(10));

        // Тестовые входы
        fitnessClub.enterZone(oneTime, Zone.POOL);
        fitnessClub.enterZone(day, Zone.POOL);
        fitnessClub.enterZone(day, Zone.GYM);
        fitnessClub.enterZone(full, Zone.GROUP);

        System.out.println("\n🔒 Клуб закрывается...");
        fitnessClub.closeClub();
    }
}
