package com.avansdevops.users;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserTest {
    @Test
    public void testDeveloper() {
        User user = new Developer("Piet");
        assertEquals("Piet", user.getName());
        assertEquals(UserRole.DEVELOPER, user.getRole());
    }

    @Test
    public void testProductOwner() {
        User user = new ProductOwner("Henk");
        assertEquals("Henk", user.getName());
        assertEquals(UserRole.PRODUCT_OWNER, user.getRole());
    }

    @Test
    public void testScrumMaster() {
        User user = new ScrumMaster("Klaas");
        assertEquals("Klaas", user.getName());
        assertEquals(UserRole.SCRUM_MASTER, user.getRole());
    }
}
