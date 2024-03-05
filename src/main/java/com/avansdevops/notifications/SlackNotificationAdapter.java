package com.avansdevops.notifications;

public class SlackNotificationAdapter implements Notification{

    @Override
    public void sendNotification(String message) {
        System.out.println("Slack notification: " + message);
    }
    
}
