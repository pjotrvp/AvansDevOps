package com.avansdevops.users;

import java.util.List;

import com.avansdevops.notifications.NotificationAdapter;

public interface User {
    String getName();

    UserRole getRole();

    void setAdapters(List<NotificationAdapter> adapters);
}
