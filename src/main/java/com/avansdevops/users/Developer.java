package com.avansdevops.users;

import com.avansdevops.notifications.NotificationAdapter;

public class Developer extends BaseUser {
    public Developer(String name) {
        super(name, UserRole.DEVELOPER);
    }

    @Override
    public void update(String message) {
        for (NotificationAdapter adapter : this.adapters) {
            adapter.sendNotification("Developer " + this.name + " received a notification: " + message);
        }
    }
}
