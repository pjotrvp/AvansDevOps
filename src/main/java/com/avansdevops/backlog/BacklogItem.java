package com.avansdevops.backlog;

import com.avansdevops.User;

public class BacklogItem {
    private String title;
    private String description;
    private int storyPoints;
    private BacklogItem[] subActivities;
    private IBacklogItemState state;
    private User storyOwner;

    public BacklogItem(String title, String description, int storyPoints) {
        this.title = title;
        this.description = description;
        this.storyPoints = storyPoints;
        this.state = new TodoState();
    }

    public void setState(IBacklogItemState state) {
        this.state = state;
    }

    public IBacklogItemState getState() {
        return state;
    }

    public void moveToTodo() {
        state.moveToTodo(this);
    }

    public void moveToDoing() {
        state.moveToDoing(this);
    }

    public void moveToReadyForTesting() {
        state.moveToReadyForTesting(this);
    }

    public void moveToTesting() {
        state.moveToTesting(this);
    }

    public void moveToTested() {
        state.moveToTested(this);
    }

    public void moveToDone() {
        state.moveToDone(this);
    }
}
