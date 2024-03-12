package com.avansdevops.notifications;

public class BacklogItemNotification implements Observer {
    private Notification notificationAdapter;

    public BacklogItemNotification(Notification notificationAdapter) {
        this.notificationAdapter = notificationAdapter;
    }

    @Override
    public void update(String message) {
        notificationAdapter.sendNotification(message);
    }
}
