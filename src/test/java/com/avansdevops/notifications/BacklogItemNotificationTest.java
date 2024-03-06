package com.avansdevops.notifications;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class BacklogItemNotificationTest {
    
    @Test
    public void testUpdate() {
        // Mock Notification adapter
        Notification notificationAdapter = mock(Notification.class);

        // Create BacklogNotification instance with the mocked adapter
        BacklogItemNotification backlogNotification = new BacklogItemNotification(notificationAdapter);

        // Define test message
        String message = "Test message";

        // Call the update method
        backlogNotification.update(message);

        // Verify that the sendNotification method of the adapter was called with the
        // correct message
        verify(notificationAdapter).sendNotification(message);
    }

    @Test
    public void testUpdateWithNullMessage() {
        // Mock Notification adapter
        Notification notificationAdapter = mock(Notification.class);

        // Create BacklogNotification instance with the mocked adapter
        BacklogItemNotification backlogNotification = new BacklogItemNotification(notificationAdapter);

        // Define test message
        String message = null;

        // Call the update method
        backlogNotification.update(message);

        // Verify that the sendNotification method of the adapter was called with the
        // correct message
        verify(notificationAdapter).sendNotification(message);
    }

    @Test
    public void testUpdateWithEmptyMessage() {
        // Mock Notification adapter
        Notification notificationAdapter = mock(Notification.class);

        // Create BacklogNotification instance with the mocked adapter
        BacklogItemNotification backlogNotification = new BacklogItemNotification(notificationAdapter);

        // Define test message
        String message = "";

        // Call the update method
        backlogNotification.update(message);

        // Verify that the sendNotification method of the adapter was called with the
        // correct message
        verify(notificationAdapter).sendNotification(message);
    }
}
