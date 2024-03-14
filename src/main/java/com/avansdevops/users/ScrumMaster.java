package com.avansdevops.users;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.notifications.NotificationAdapter;
import com.avansdevops.notifications.Observer;

public class ScrumMaster implements User, Observer {
    private String name;
    private UserRole role;
    private List<NotificationAdapter> adapters = new ArrayList<>();

    public ScrumMaster(String name) {
        this.name = name;
        this.role = UserRole.SCRUM_MASTER;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UserRole getRole() {
        return this.role;
    }

    @Override
    public void setAdapters(List<NotificationAdapter> adapters) {
        this.adapters = adapters;
    }

    @Override
    public void update(String message) {
        for (NotificationAdapter adapter : this.adapters) {
            adapter.sendNotification("Scrum master " + this.name + " received a notification: " + message);
        }
    }
}
