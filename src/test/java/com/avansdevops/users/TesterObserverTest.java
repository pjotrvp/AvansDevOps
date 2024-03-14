package com.avansdevops.users;

import com.avansdevops.notifications.NotificationAdapter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.verify;

public class TesterObserverTest {
    private Tester tester;
    private NotificationAdapter mockAdapter;

    @Before
    public void setUp() {
        tester = new Tester("Piet");
        mockAdapter = Mockito.mock(NotificationAdapter.class);
        tester.setAdapters(Arrays.asList(mockAdapter));
    }

    @Test
    public void testUpdate() {
        String message = "Test message";
        tester.update(message);
        verify(mockAdapter).sendNotification("Tester Piet received a notification: " + message);
    }
}