package com.avansdevops.backlog;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.notifications.Observer;

public class TestingState implements IBacklogItemState {
    private List<Observer> observers;

    public TestingState() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TodoState(), ItemStatus.TESTING, ItemStatus.TODO);
        // notify observers
        notifyObservers("Backlog item " + backlogItem.getTitle() + " is moved to TODO");
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.TESTING, ItemStatus.DOING);
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new ReadyForTestingState(), ItemStatus.TESTING, ItemStatus.READY_FOR_TESTING);
        // notify scrum master
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.TESTING, ItemStatus.TESTING);
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TestedState(), ItemStatus.TESTING, ItemStatus.TESTED);
        // notify observers
        notifyObservers("Backlog item " + backlogItem.getTitle() + " is moved to TESTED");

    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.TESTING, ItemStatus.DONE);
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
