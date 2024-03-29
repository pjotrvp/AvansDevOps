package com.avansdevops.backlog;

public class ReadyForTestingState implements IBacklogItemState {
    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.READY_FOR_TESTING, ItemStatus.TODO);
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new DoingState(), ItemStatus.READY_FOR_TESTING, ItemStatus.DOING);
        backlogItem.notifyScrumMaster();
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.READY_FOR_TESTING, ItemStatus.READY_FOR_TESTING);
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TestingState(), ItemStatus.READY_FOR_TESTING, ItemStatus.TESTING);
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.READY_FOR_TESTING, ItemStatus.TESTED);
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.READY_FOR_TESTING, ItemStatus.DONE);
    }
}
