package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager manager;

    @BeforeEach
    void setUp() {
        manager = new UserManager();
        manager.addUser("Егор", 20, "мужской");
        manager.addUser("Анна", 22, "женский");
        manager.addUser("Иван", 25, "мужской");
    }

    @Test
    void testPrintAllUsers() {
        assertEquals(3, manager.getAllUsers().size());
    }

    @Test
    void testMaleUsers() {
        List<User> males = manager.getMaleUsers();
        assertEquals(2, males.size());
        assertTrue(males.stream().allMatch(u -> u.getGender().equals("мужской")));
    }

    @Test
    void testFemaleUsers() {
        List<User> females = manager.getFemaleUsers();
        assertEquals(1, females.size());
        assertTrue(females.stream().allMatch(u -> u.getGender().equals("женский")));
    }

    @Test
    void testTotalUsers() {
        assertEquals(3, manager.getTotalUsers());
    }

    @Test
    void testAverageAge() {
        assertEquals((20 + 22 + 25) / 3.0, manager.getAverageAge());
    }
}
