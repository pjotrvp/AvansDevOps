package com.avansdevops.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class UserFactoryTest {
    private UserFactory factory;

    @Before
    public void setUp() {
        factory = new UserFactory();
    }

    @Test
    public void testCreateProductOwner() {
        User productOwner = factory.createUser(UserRole.PRODUCT_OWNER, "PO");
        assertTrue(productOwner instanceof ProductOwner);
        assertEquals("PO", productOwner.getName());
    }

    @Test
    public void testCreateScrumMaster() {
        User scrumMaster = factory.createUser(UserRole.SCRUM_MASTER, "SM");
        assertTrue(scrumMaster instanceof ScrumMaster);
        assertEquals("SM", scrumMaster.getName());
    }

    @Test
    public void testCreateDeveloper() {
        User developer = factory.createUser(UserRole.DEVELOPER, "Dev");
        assertTrue(developer instanceof Developer);
        assertEquals("Dev", developer.getName());
    }

    @Test
    public void testCreateUnknown() {
        User unknown = factory.createUser(UserRole.TESTER, "Unknown");
        assertNull(unknown);
    }
}