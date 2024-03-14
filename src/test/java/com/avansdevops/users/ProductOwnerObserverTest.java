package com.avansdevops.users;

import com.avansdevops.notifications.NotificationAdapter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.verify;

public class ProductOwnerObserverTest {
    private ProductOwner productOwner;
    private NotificationAdapter mockAdapter;

    @Before
    public void setUp() {
        productOwner = new ProductOwner("Piet");
        mockAdapter = Mockito.mock(NotificationAdapter.class);
        productOwner.setAdapters(Arrays.asList(mockAdapter));
    }

    @Test
    public void testUpdate() {
        String message = "Test message";
        productOwner.update(message);
        verify(mockAdapter).sendNotification("Product owner Piet received a notification: " + message);
    }
}