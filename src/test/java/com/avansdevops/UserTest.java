package com.avansdevops;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserTest {
    @Test
    public void testUser() {
        User user = new User("John Doe", UserRole.DEVELOPER);
        assertEquals("John Doe", user.getName());
    }
}
