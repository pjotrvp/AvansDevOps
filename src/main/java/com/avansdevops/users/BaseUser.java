package com.avansdevops.users;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.notifications.NotificationAdapter;
import com.avansdevops.notifications.Observer;

public abstract class BaseUser implements User, Observer {
    protected String name;
    protected UserRole role;
    protected List<NotificationAdapter> adapters = new ArrayList<>();

    protected BaseUser(String name, UserRole role) {
        this.name = name;
        this.role = role;
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
}
