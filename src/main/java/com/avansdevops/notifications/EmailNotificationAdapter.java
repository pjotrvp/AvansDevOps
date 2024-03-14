package com.avansdevops.notifications;

public class EmailNotificationAdapter implements NotificationAdapter {
    @Override
    public void sendNotification(String message) {
        System.out.println("Email notification: " + message);
    }
}
