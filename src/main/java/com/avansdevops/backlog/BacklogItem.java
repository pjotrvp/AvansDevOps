package com.avansdevops.backlog;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.notifications.BaseSubject;
import com.avansdevops.notifications.Observer;
import com.avansdevops.users.User;
import com.avansdevops.users.UserRole;

public class BacklogItem extends BaseSubject {
    private String title;
    private String description;
    private int storyPoints;
    private List<BacklogItem> subTasks = new ArrayList<>();
    private IBacklogItemState state;
    private User assignee;
    private boolean isSubTask = false;
    private String code;

    private static String backlogItemText = "Backlog item ";

    public BacklogItem(String title, String description, int storyPoints) {
        this.title = title;
        this.description = description;
        this.storyPoints = storyPoints;
        this.state = new WaitingState();
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public IBacklogItemState getState() {
        return state;
    }

    public void setState(IBacklogItemState state) {
        this.state = state;
    }

    public synchronized void moveToTodo() {
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

    public void notifyTesters() {
        if (getState() instanceof ReadyForTestingState) {
            notifyObservers(UserRole.TESTER, backlogItemText + getTitle() + " is ready for testing");
        }
    }

    public void notifyScrumMaster() {
        notifyObservers(UserRole.SCRUM_MASTER, backlogItemText + getTitle() + " has been moved to " + getState());
    }

    public void notifyAssignee() {
        if (getState() instanceof TodoState && assignee != null) {
            ((Observer) assignee).update(backlogItemText + getTitle() + " has been moved back to " + getState());
        }
    }

    @Override
    public void notifyObservers(UserRole role, String message) {
        for (Observer observer : observers) {
            if (((User) observer).getRole().equals(role)) {
                observer.update(message);
            }
        }
    }
}
