package com.avansdevops.users;

import com.avansdevops.notifications.NotificationAdapter;

public class ProductOwner extends BaseUser {
    public ProductOwner(String name) {
        super(name, UserRole.PRODUCT_OWNER);
    }

    @Override
    public void update(String message) {
        for (NotificationAdapter adapter : this.adapters) {
            adapter.sendNotification("Product owner " + this.name + " received a notification: " + message);
        }
    }
}
