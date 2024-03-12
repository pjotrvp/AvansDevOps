package com.avansdevops.notifications;


import org.junit.Test;
import static org.mockito.Mockito.*;

public class SprintNotificationTest {
    @Test
    public void testUpdate() {
        // Mock Notification adapter
        Notification notificationAdapter = mock(Notification.class);

        // Create SprintNotification instance with the mocked adapter
        SprintNotification sprintNotification = new SprintNotification(notificationAdapter);

        // Define test message
        String message = "Test message";

        // Call the update method
        sprintNotification.update(message);

        // Verify that the sendNotification method of the adapter was called with the
        // correct message
        verify(notificationAdapter).sendNotification(message);
        
    }

    @Test
    public void testUpdateWithNullMessage() {
        // Mock Notification adapter
        Notification notificationAdapter = mock(Notification.class);

        // Create SprintNotification instance with the mocked adapter
        SprintNotification sprintNotification = new SprintNotification(notificationAdapter);

        // Define test message
        String message = null;

        // Call the update method
        sprintNotification.update(message);

        // Verify that the sendNotification method of the adapter was called with the
        // correct message
        verify(notificationAdapter).sendNotification(message);
        
    }

    @Test
    public void testUpdateWithEmptyMessage() {
        // Mock Notification adapter
        Notification notificationAdapter = mock(Notification.class);

        // Create SprintNotification instance with the mocked adapter
        SprintNotification sprintNotification = new SprintNotification(notificationAdapter);

        // Define test message
        String message = "";

        // Call the update method
        sprintNotification.update(message);

        // Verify that the sendNotification method of the adapter was called with the
        // correct message
        verify(notificationAdapter).sendNotification(message);
        
    }
}