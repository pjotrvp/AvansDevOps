package com.avansdevops.notifications;

import java.util.List;

import com.avansdevops.users.UserRole;

public interface Subject {
    void setObservers(List<Observer> observers);

    void removeObserver(Observer observer);

    void notifyObservers(UserRole role, String message);
}
