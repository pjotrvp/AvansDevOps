package com.avansdevops.backlog;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.Sprint;
import com.avansdevops.notifications.Observer;
import com.avansdevops.notifications.Subject;
import com.avansdevops.users.User;
import com.avansdevops.users.UserRole;

public class BacklogItem implements Subject {
    private String title;
    private String description;
    private int storyPoints;
    private List<BacklogItem> subTasks = new ArrayList<>();
    private IBacklogItemState state;
    private User assignee;
    private boolean isSubTask = false;
    private String code;
    private List<Observer> observers = new ArrayList<>();
    private Sprint sprint;

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

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public IBacklogItemState getState() {
        return state;
    }

    public void setState(IBacklogItemState state) {
        this.state = state;
    }

    public void moveToTodo() {
        state.moveToTodo(this);
        notifyAssignee();
    }

    public void moveToDoing() {
        state.moveToDoing(this);
        notifyScrumMaster();
    }

    public void moveToReadyForTesting() {
        state.moveToReadyForTesting(this);
        notifyTesters();
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

    public void notifyTesters() throws IllegalStateException {
        if (getState() instanceof ReadyForTestingState) {
            notifyObservers(UserRole.TESTER, "Backlog item " + getTitle() + " is ready for testing");
        } else {
            throw new IllegalStateException("Backlog item is not in ready for testing");
        }
    }

    public void notifyScrumMaster() {
        notifyObservers(UserRole.SCRUM_MASTER, "Backlog item " + getTitle() + " has been moved to " + getState());
    }

    public void notifyAssignee() throws IllegalStateException {
        if (getState() instanceof TodoState && assignee != null) {
            ((Observer) assignee).update("Backlog item " + getTitle() + " has been moved back to " + getState());
        }
        throw new IllegalStateException("Backlog item is not in Todo state");
    }

    @Override
    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }

    @Override
    public void removeObserver(Observer observer) throws IllegalArgumentException {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
        throw new IllegalArgumentException("Observer not found");
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
