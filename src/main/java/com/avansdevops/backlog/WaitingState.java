package com.avansdevops.backlog;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.notifications.Observer;

public class WaitingState implements IBacklogItemState {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TodoState(), ItemStatus.WAITING, ItemStatus.TODO);
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.WAITING, ItemStatus.DOING);
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.WAITING, ItemStatus.READY_FOR_TESTING);
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.WAITING, ItemStatus.TESTING);
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.WAITING, ItemStatus.TESTED);
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.WAITING, ItemStatus.DONE);
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            if (observer != null) {
                observer.update(message);
            }
        }
    }
}
