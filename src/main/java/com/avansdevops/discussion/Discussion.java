package com.avansdevops.discussion;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.backlog.DoneState;

public class Discussion {
    private String title;
    private String content;
    private List<String> responses = new ArrayList<>();
    private BacklogItem backlogItem;

    public Discussion(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BacklogItem getBacklogItem() {
        return backlogItem;
    }

    public void setBacklogItem(BacklogItem backlogItem) {
        this.backlogItem = backlogItem;
    }

    public List<String> getResponses() {
        return responses;
    }

    public void addResponse(String response) {
        responses.add(response);
    }

    public void moveBacklogItem(BacklogItem backlogItem) throws IllegalArgumentException, IllegalStateException {
        if (backlogItem == null) {
            throw new IllegalArgumentException("No backlog item linked to this discussion");
        }

        if (!(backlogItem.getState() instanceof DoneState)) {
            throw new IllegalStateException("Backlog item must be in the Done state to move it to the Todo state");
        }
        backlogItem.moveToTodo();
    }
}
