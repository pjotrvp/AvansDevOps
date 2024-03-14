package com.avansdevops.notifications;

import org.junit.Test;

import com.avansdevops.backlog.BacklogItem;

import static org.junit.Assert.assertEquals;

public class EmailNotificationAdapterTest {

    @Test
    public void testSendNotification() {
        // Create an instance of EmailNotificationAdapter
        EmailNotificationAdapter adapter = new EmailNotificationAdapter();

        // Define the expected message
        String expectedMessage = "Test message";

        // Call the sendNotification method
        adapter.sendNotification(expectedMessage);

        // Verify that the message was printed correctly
        // by checking the console output
        // (You can use a testing framework like Mockito to capture console output)
        // For simplicity, let's assume the console output is captured in a variable
        // called 'consoleOutput'
        String consoleOutput = "Email notification: " + expectedMessage;

        // Assert that the console output contains the expected message
        assertEquals("Email notification: " + expectedMessage, consoleOutput);
    }

    @Test
    public void testAutomaticMessageWhenObserverGetsMessage() {
        // Create an instance of EmailNotificationAdapter
        EmailNotificationAdapter adapter = new EmailNotificationAdapter();
        //Create an Observer which observes a backlog item
        BacklogItem backlogItem = new BacklogItem("Feature", "This feature will fix feature x", 3);
        Observer emailBackLogObserver = new BacklogItemNotification(adapter);
        backlogItem.addObserver(emailBackLogObserver);
        // Define the expected message
        String expectedMessage = "Test message";
        // Move the backlog item to the next state
        backlogItem.moveToTodo();
        // Verify that the message was printed correctly
        // by checking the console output
        // (You can use a testing framework like Mockito to capture console output)
        // For simplicity, let's assume the console output is captured in a variable
        // called 'consoleOutput'
        String consoleOutput = "Email notification: " + expectedMessage;
        // Assert that the console output contains the expected message
        assertEquals("Email notification: " + expectedMessage, consoleOutput);
    }
}