package com.avansdevops;

import com.avansdevops.BacklogItems.IBacklogItemState;
import com.avansdevops.BacklogItems.TodoState;

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
}
