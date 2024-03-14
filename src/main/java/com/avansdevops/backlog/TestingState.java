package com.avansdevops.backlog;

public class TestingState implements IBacklogItemState {
    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TodoState(), ItemStatus.TESTING, ItemStatus.TODO);
        backlogItem.notifyAssignee();
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.TESTING, ItemStatus.DOING);
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new ReadyForTestingState(), ItemStatus.TESTING, ItemStatus.READY_FOR_TESTING);
        backlogItem.notifyTesters();
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.TESTING, ItemStatus.TESTING);
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        printAndSetState(backlogItem, new TestedState(), ItemStatus.TESTING, ItemStatus.TESTED);
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        printAndSetState(backlogItem, null, ItemStatus.TESTING, ItemStatus.DONE);
    }
}
