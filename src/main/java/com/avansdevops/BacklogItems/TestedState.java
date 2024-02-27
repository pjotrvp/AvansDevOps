package com.avansdevops.BacklogItems;

import com.avansdevops.BacklogItem;

public class TestedState implements IBacklogItemState {

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        System.out.println("Cannot move to Todo from Tested");
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        System.out.println("Cannot move to Doing from Tested");
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        System.out.println("Cannot move to Ready for Testing from Tested");
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        System.out.println("Move to Testing from Tested");
        backlogItem.setState(new TestingState());
        // notify observers
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        System.out.println("Already in Tested");
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        System.out.println("Moving to Done from Tested");
        backlogItem.setState(new DoneState());
        // notify observers
    }

}
