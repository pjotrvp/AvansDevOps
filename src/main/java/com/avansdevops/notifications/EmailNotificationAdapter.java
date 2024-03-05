package com.avansdevops.notifications;

public class EmailNotificationAdapter implements Notification {

    @Override
    public void sendNotification(String message) {
        System.out.println("Email notification: " + message);
    }
    
}
