package com.avansdevops.notifications;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSubject implements Subject {
    protected List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        if (observers.contains(observer)) {
            throw new IllegalArgumentException("Observer already exists");
        }
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if (!observers.contains(observer)) {
            throw new IllegalArgumentException("Observer not found");
        }
        observers.remove(observer);
    }

    @Override
    public List<Observer> getObservers() {
        return this.observers;
    }
}