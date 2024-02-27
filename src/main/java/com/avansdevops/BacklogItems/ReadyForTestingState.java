package com.avansdevops.BacklogItems;

import com.avansdevops.BacklogItem;

public class ReadyForTestingState implements IBacklogItemState {

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        System.out.println("Cannot move to Todo from ReadyForTesting");
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        System.out.println("Moving to Doing from Ready For Testing");
        backlogItem.setState(new DoingState());
        // notify scrum master
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        System.out.println("Already in Ready For Testing");
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        System.out.println("Moving to Testing from Ready For Testing");
        backlogItem.setState(new TestingState());
        // notify observers
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        System.out.println("Cannot move to Tested from Ready For Testing");
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        System.out.println("Cannot move to Done from Ready For Testing");
    }

}
