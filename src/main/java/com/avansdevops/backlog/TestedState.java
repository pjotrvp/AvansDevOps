package com.avansdevops.backlog;

public class TestedState implements IBacklogItemState {
    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        printMessage("Cannot move to Todo from Tested");
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        printMessage("Cannot move to Doing from Tested");
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        printMessage("Cannot move to Ready for Testing from Tested");
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TestingState(), "Move to Testing from Tested");
        // notify observers
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        printMessage("Already in Tested");
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new DoneState(), "Moving to Done from Tested");
        // notify observers
    }
}
