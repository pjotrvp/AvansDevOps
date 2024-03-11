package com.avansdevops.backlog;

public class ReadyForTestingState implements IBacklogItemState {

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        printMessage("Cannot move to Todo from ReadyForTesting");
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new DoingState(), "Moving to Doing from Ready For Testing");
        // notify scrum master
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        printMessage("Already in Ready For Testing");
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TestingState(), "Moving to Testing from Ready For Testing");
        // notify observers
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        printMessage("Cannot move to Tested from Ready For Testing");
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        printMessage("Cannot move to Done from Ready For Testing");
    }
}
