package com.avansdevops.backlog;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.notifications.Observer;

public class TestedState implements IBacklogItemState {
    private List<Observer> observers;

    public TestedState() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.TESTED, ItemStatus.TODO);
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.TESTED, ItemStatus.DOING);
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.TESTED, ItemStatus.READY_FOR_TESTING);
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TestingState(), ItemStatus.TESTED, ItemStatus.TESTING);
        // notify observers
        notifyObservers("Backlog item " + backlogItem.getTitle() + " is moved to TESTING");
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.TESTED, ItemStatus.TESTED);
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new DoneState(), ItemStatus.TESTED, ItemStatus.DONE);
        // notify observers
        notifyObservers("Backlog item " + backlogItem.getTitle() + " is moved to DONE");
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
