package com.avansdevops.users;

import com.avansdevops.notifications.NotificationAdapter;

public class Tester extends BaseUser {
    public Tester(String name) {
        super(name, UserRole.TESTER);
    }

    @Override
    public void update(String message) {
        for (NotificationAdapter adapter : this.adapters) {
            adapter.sendNotification("Tester " + this.name + " received a notification: " + message);
        }
    }
}
