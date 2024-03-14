package com.avansdevops.backlog;

import java.util.ArrayList;
import java.util.List;

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

    private static String BACKLOG_ITEM_TEXT = "Backlog item ";

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
            notifyObservers(UserRole.TESTER, BACKLOG_ITEM_TEXT + getTitle() + " is ready for testing");
        }
    }

    public void notifyScrumMaster() {
        notifyObservers(UserRole.SCRUM_MASTER, BACKLOG_ITEM_TEXT + getTitle() + " has been moved to " + getState());
    }

    public void notifyAssignee() {
        if (getState() instanceof TodoState && assignee != null) {
            ((Observer) assignee).update(BACKLOG_ITEM_TEXT + getTitle() + " has been moved back to " + getState());
        }
    }

    @Override
    public void addObserver(Observer observer) {
        if (observers.contains(observer)) {
            throw new IllegalArgumentException("Observer already exists");
        }
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) throws IllegalArgumentException {
        if (!observers.contains(observer)) {
            throw new IllegalArgumentException("Observer not found");
        }
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(UserRole role, String message) {
        for (Observer observer : observers) {
            if (((User) observer).getRole().equals(role)) {
                observer.update(message);
            }
        }
    }

    @Override
    public List<Observer> getObservers() {
        return this.observers;
    }
}
