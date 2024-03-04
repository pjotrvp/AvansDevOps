package com.avansdevops.backlog;

import com.avansdevops.BacklogItem;

public class DoneState implements IBacklogItemState {

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        System.out.println("Cannot move to Todo from Done");
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        System.out.println("Cannot move to Doing from Done");
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        System.out.println("Cannot move to Ready for Testing from Done");
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        System.out.println("Cannot move to Testing from Done");
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        System.out.println("Cannot move to Tested from Done");
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        System.out.println("Already in Done");
    }

}
