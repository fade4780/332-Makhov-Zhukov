package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthManagerTest {

    @Test
    public void testSuccessfulRegister() {
        AuthManager auth = new AuthManager();
        auth.register("user_1", "password1", "password1");
        assertEquals(1, auth.getUserCount());
    }

    @Test
    public void testPasswordMismatch() {
        AuthManager auth = new AuthManager();
        assertThrows(IllegalArgumentException.class, () -> {
            auth.register("user_2", "pass123", "pass124");
        });
    }

    @Test
    public void testInvalidLogin() {
        AuthManager auth = new AuthManager();
        assertThrows(IllegalArgumentException.class, () -> {
            auth.register("логин", "password1", "password1");
        });
    }

    @Test
    public void testPasswordLength() {
        AuthManager auth = new AuthManager();
        assertThrows(IllegalArgumentException.class, () -> {
            auth.register("user_3", "short", "short");
        });
    }
}
