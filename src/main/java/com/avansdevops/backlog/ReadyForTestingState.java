package com.avansdevops.backlog;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.notifications.Observer;

public class ReadyForTestingState implements IBacklogItemState {
    private List<Observer> observers;

    public ReadyForTestingState() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.READY_FOR_TESTING, ItemStatus.TODO);
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new DoingState(), ItemStatus.READY_FOR_TESTING, ItemStatus.DOING);
        // notify scrum master
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.READY_FOR_TESTING, ItemStatus.READY_FOR_TESTING);
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TestingState(), ItemStatus.READY_FOR_TESTING, ItemStatus.TESTING);
        // notify observers
        notifyObservers("Backlog item " + backlogItem.getTitle() + " is moved to TESTING");
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.READY_FOR_TESTING, ItemStatus.TESTED);
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.READY_FOR_TESTING, ItemStatus.DONE);
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