package fitness;

public class Member {
    private String firstName;
    private String lastName;
    private int birthYear;

    public Member(String firstName, String lastName, int birthYear) {
        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Имя и фамилия не могут быть пустыми!");
        if (birthYear < 1900 || birthYear > java.time.LocalDate.now().getYear())
            throw new IllegalArgumentException("Некорректный год рождения!");

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return getFullName() + " (" + birthYear + ")";
    }
}
