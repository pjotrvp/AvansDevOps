package com.avansdevops.notifications;

import java.util.List;

import com.avansdevops.users.UserRole;

public interface Subject {
    List<Observer> getObservers();

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(UserRole role, String message);
}
