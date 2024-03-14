package com.avansdevops.users;

import com.avansdevops.notifications.NotificationAdapter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.verify;

public class DeveloperObserverTest {
    private Developer developer;
    private NotificationAdapter mockAdapter;

    @Before
    public void setUp() {
        developer = new Developer("Piet");
        mockAdapter = Mockito.mock(NotificationAdapter.class);
        developer.setAdapters(Arrays.asList(mockAdapter));
    }

    @Test
    public void testUpdate() {
        String message = "Test message";
        developer.update(message);
        verify(mockAdapter).sendNotification("Developer Piet received a notification: " + message);
    }
}