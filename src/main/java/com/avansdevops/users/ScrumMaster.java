package com.avansdevops.users;

import com.avansdevops.notifications.NotificationAdapter;

public class ScrumMaster extends BaseUser {
    public ScrumMaster(String name) {
        super(name, UserRole.SCRUM_MASTER);
    }

    @Override
    public void update(String message) {
        for (NotificationAdapter adapter : this.adapters) {
            adapter.sendNotification("Scrum master " + this.name + " received a notification: " + message);
        }
    }
}
