package com.avansdevops.notifications;

public class SprintNotification implements Observer{
    private Notification notificationAdapter;

    public SprintNotification(Notification notificationAdapter) {
        this.notificationAdapter = notificationAdapter;
    }

    @Override
    public void update(String message) {
        notificationAdapter.sendNotification(message);
    }
    
}
