package com.avansdevops;

public class TestingState implements IBacklogItemState {

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        System.out.println("Moving to Todo from Testing");
        backlogItem.setState(new TodoState());
        // notify observers
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        System.out.println("Cannot move to Doing from Testing");
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        System.out.println("Moving to Ready for Testing from Testing");
        backlogItem.setState(new ReadyForTestingState());
        // notify scrum master
    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        System.out.println("Already in Testing");
    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        System.out.println("Moving to Tested from Testing");
        backlogItem.setState(new TestedState());
        // notify observers
    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        System.out.println("Cannot move to Done from Testing");
    }

}
