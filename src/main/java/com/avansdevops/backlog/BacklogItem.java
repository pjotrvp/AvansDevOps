package com.avansdevops.backlog;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.users.User;

public class BacklogItem {
    private String title;
    private String description;
    private int storyPoints;
    private List<BacklogItem> subTasks = new ArrayList<>();
    private IBacklogItemState state;
    private User assignee;
    private boolean isSubTask = false;

    public BacklogItem(String title, String description, int storyPoints) {
        this.title = title;
        this.description = description;
        this.storyPoints = storyPoints;
        this.state = new TodoState();
    }

    public List<BacklogItem> getSubTasks() {
        return subTasks;
    }

    public void addSubTask(BacklogItem subTask) throws IllegalStateException {
        if (isSubTask) {
            throw new IllegalStateException("A subtask cannot have subtasks");
        }
        subTask.isSubTask = true;
        subTasks.add(subTask);
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
    }

    public IBacklogItemState getState() {
        return state;
    }

    public void setState(IBacklogItemState state) {
        this.state = state;
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

    public void moveToDone() throws IllegalStateException {
        if (!isSubTask) {
            for (BacklogItem subTask : subTasks) {
                if (!(subTask.getState() instanceof DoneState)) {
                    throw new IllegalStateException(
                            "All subtasks must be Done before the main item can be moved to Done");
                }
            }
        }
        state.moveToDone(this);
    }
}
