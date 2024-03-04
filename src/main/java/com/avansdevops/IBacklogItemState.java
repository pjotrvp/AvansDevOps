package com.avansdevops;

public interface IBacklogItemState {
    void moveToTodo(BacklogItem backlogItem);

    void moveToDoing(BacklogItem backlogItem);

    void moveToReadyForTesting(BacklogItem backlogItem);

    void moveToTesting(BacklogItem backlogItem);

    void moveToTested(BacklogItem backlogItem);

    void moveToDone(BacklogItem backlogItem);
}
