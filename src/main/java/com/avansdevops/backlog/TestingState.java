package com.avansdevops.backlog;

public class TestingState implements IBacklogItemState {

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TodoState(), "Moving to Todo from Testing");
        // notify observers
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        printMessage("Cannot move to Doing from Testing");
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new ReadyForTestingState(), "Moving to Ready for Testing from Testing");
        // notify scrum master
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        printMessage("Already in Testing");
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TestedState(), "Moving to Tested from Testing");
        // notify observers
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        printMessage("Cannot move to Done from Testing");
    }
}
