package com.avansdevops.backlog;

public class DoneState implements IBacklogItemState {
    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TodoState(), ItemStatus.DONE, ItemStatus.TODO);
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.DONE, ItemStatus.DOING);
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.DONE, ItemStatus.READY_FOR_TESTING);
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.DONE, ItemStatus.TESTING);
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.DONE, ItemStatus.TESTED);
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.DONE, ItemStatus.DONE);
    }

}
