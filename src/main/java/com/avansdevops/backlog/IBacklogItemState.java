package com.avansdevops.backlog;

public interface IBacklogItemState {
    void moveToTodo(BacklogItem backlogItem);

    void moveToDoing(BacklogItem backlogItem);

    void moveToReadyForTesting(BacklogItem backlogItem);

    void moveToTesting(BacklogItem backlogItem);

    void moveToTested(BacklogItem backlogItem);

    void moveToDone(BacklogItem backlogItem);

    default String generateMessage(String currentState, String targetState, boolean canMove) {
        if (currentState.equals(targetState)) {
            return "Already in " + currentState;
        } else if (canMove) {
            return "Moving to " + targetState + " from " + currentState;
        } else {
            return "Cannot move to " + targetState + " from " + currentState;
        }
    }

    default void printAndSetState(BacklogItem backlogItem, IBacklogItemState newState, String currentState,
            String targetState) {
        String message = generateMessage(currentState, targetState, newState != null);
        System.out.println(message);
        if (newState != null) {
            backlogItem.setState(newState);
        }
    }
}
