package com.avansdevops.notifications;

public class SmsNotificationAdapter implements Notification {

    @Override
    public void sendNotification(String message) {
        System.out.println("SMS notification: " + message);
    }
    
}
