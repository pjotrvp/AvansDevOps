package com.avansdevops.backlog;

import com.avansdevops.BacklogItem;

public class TodoState implements IBacklogItemState {

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        System.out.println("Already in Todo");
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        System.out.println("Moving to Doing from Todo");
        backlogItem.setState(new DoingState());
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        System.out.println("Cannot move to Ready for Testing from Todo");
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        System.out.println("Cannot move to Testing from Todo");
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        System.out.println("Cannot move to Tested from Todo");
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        System.out.println("Cannot move to Done from Todo");
    }

}
