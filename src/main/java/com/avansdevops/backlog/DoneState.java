package com.avansdevops.backlog;

public class DoneState implements IBacklogItemState {

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TodoState(), "Moving to Todo from Done");
        // notify scum master
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        printMessage("Cannot move to Doing from Done");
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        printMessage("Cannot move to Ready for Testing from Done");
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        printMessage("Cannot move to Testing from Done");
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        printMessage("Cannot move to Tested from Done");
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        printMessage("Already in Done");
    }
}
