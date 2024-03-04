package com.avansdevops;

public class DoingState implements IBacklogItemState {

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        System.out.println("Moving to Todo from Doing");
        backlogItem.setState(new TodoState());
        // notify scum master
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        System.out.println("Already in Doing");
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        System.out.println("Moving to Ready for Testing from Doing");
        backlogItem.setState(new ReadyForTestingState());
        // notify observers
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        System.out.println("Cannot move to Testing from Doing");
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        System.out.println("Cannot move to Tested from Doing");
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        System.out.println("Cannot move to Done from Doing");
    }

}
