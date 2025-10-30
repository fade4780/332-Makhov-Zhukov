package org.example;

public class User {
    private static int counter = 1;
    private final int id;
    private final String name;
    private final int age;
    private final String gender;

    public User(String name, int age, String gender) {
        this.id = counter++;
        this.name = name;
        this.age = age;
        this.gender = gender.toLowerCase();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
