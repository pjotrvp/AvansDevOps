package com.avansdevops.backlog;

public class WaitingState implements IBacklogItemState {

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TodoState(), ItemStatus.WAITING, ItemStatus.TODO);
        backlogItem.notifyAssignee();
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.WAITING, ItemStatus.DOING);
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.WAITING, ItemStatus.READY_FOR_TESTING);
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.WAITING, ItemStatus.TESTING);
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.WAITING, ItemStatus.TESTED);
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.WAITING, ItemStatus.DONE);
    }

}
