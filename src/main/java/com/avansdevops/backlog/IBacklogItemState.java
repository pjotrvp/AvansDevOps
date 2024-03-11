package com.avansdevops.backlog;

public interface IBacklogItemState {
    void moveToTodo(BacklogItem backlogItem);

    void moveToDoing(BacklogItem backlogItem);

    void moveToReadyForTesting(BacklogItem backlogItem);

    void moveToTesting(BacklogItem backlogItem);

    void moveToTested(BacklogItem backlogItem);

    void moveToDone(BacklogItem backlogItem);

    default void printMessage(String message) {
        System.out.println(message);
    }

    default void printAndSetState(BacklogItem backlogItem, IBacklogItemState newState, String message) {
        printMessage(message);
        backlogItem.setState(newState);
    }
}
