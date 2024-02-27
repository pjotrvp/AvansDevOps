package com.avansdevops.BacklogItems;

import com.avansdevops.BacklogItem;

public class WaitingState implements IBacklogItemState{

    @Override
    public void moveToTodo(BacklogItem backlogItem) {
        System.out.println("Item is not in a sprint yet");
    }

    @Override
    public void moveToDoing(BacklogItem backlogItem) {
        System.out.println("Item is not in a sprint yet");
    }

    @Override
    public void moveToReadyForTesting(BacklogItem backlogItem) {
        System.out.println("Item is not in a sprint yet");

    }

    @Override
    public void moveToTesting(BacklogItem backlogItem) {
        System.out.println("Item is not in a sprint yet");

    }

    @Override
    public void moveToTested(BacklogItem backlogItem) {
        System.out.println("Item is not in a sprint yet");

    }

    @Override
    public void moveToDone(BacklogItem backlogItem) {
        System.out.println("Item is not in a sprint yet");

    }
    
}
