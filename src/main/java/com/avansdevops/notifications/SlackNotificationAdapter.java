package com.avansdevops.notifications;

public class SlackNotificationAdapter implements NotificationAdapter {
    @Override
    public void sendNotification(String message) {
        System.out.println("Slack notification: " + message);
    }
}
