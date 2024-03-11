package com.avansdevops.notifications;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BacklogItemNotificationTest {

    @ParameterizedTest
    @ValueSource(strings = { "Test message", "", "" })
    public void testUpdate(String message) {
        // Mock Notification adapter
        Notification notificationAdapter = mock(Notification.class);

        // Create BacklogNotification instance with the mocked adapter
        BacklogItemNotification backlogNotification = new BacklogItemNotification(notificationAdapter);

        // Call the update method
        backlogNotification.update(message);

        // Verify that the sendNotification method of the adapter was called with the
        // correct message
        verify(notificationAdapter).sendNotification(message);
    }
}
