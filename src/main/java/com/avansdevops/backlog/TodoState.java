package com.avansdevops.backlog;

public class TodoState implements IBacklogItemState {

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        printMessage("Already in Todo");
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new DoingState(), "Moving to Doing from Todo");
        // notify observers
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        printMessage("Cannot move to Ready for Testing from Todo");
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        printMessage("Cannot move to Testing from Todo");
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        printMessage("Cannot move to Tested from Todo");
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        printMessage("Cannot move to Done from Todo");
    }
}
