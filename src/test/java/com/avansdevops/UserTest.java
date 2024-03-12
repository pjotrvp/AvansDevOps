package com.avansdevops;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.avansdevops.users.Developer;
import com.avansdevops.users.User;

public class UserTest {
    @Test
    public void testUser() {
        User user = new Developer("John Doe");
        assertEquals("John Doe", user.getName());
    }
}
