package com.avansdevops.backlog;

public class DoingState implements IBacklogItemState {

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TodoState(), "Moving to Todo from Doing");
        // notify scum master
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        printMessage("Already in Doing");
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new ReadyForTestingState(), "Moving to Ready for Testing from Doing");
        // notify observers
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        printMessage("Cannot move to Testing from Doing");
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        printMessage("Cannot move to Tested from Doing");
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        printMessage("Cannot move to Done from Doing");
    }
}
