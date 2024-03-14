package com.avansdevops.discussion;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.backlog.DoneState;
import com.avansdevops.notifications.Observer;
import com.avansdevops.notifications.Subject;
import com.avansdevops.users.User;
import com.avansdevops.users.UserRole;

public class Discussion implements Subject {
    private String title;
    private String content;
    private List<String> responses = new ArrayList<>();
    private BacklogItem backlogItem;
    private User creator;
    private List<Observer> observers = new ArrayList<>();

    public Discussion(String title, String content, User creator) {
        this.title = title;
        this.content = content;
        this.creator = creator;
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

    public User getCreator() {
        return creator;
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
        notifyObservers(null, "New response: " + response + " added to discussion: " + title);
    }

    public void moveBacklogItem() throws IllegalArgumentException, IllegalStateException {
        if (this.backlogItem == null) {
            throw new IllegalArgumentException("No backlog item linked to this discussion");
        }

        if (!(this.backlogItem.getState() instanceof DoneState)) {
            throw new IllegalStateException("Backlog item must be in the Done state to move it to the Todo state");
        }
        this.backlogItem.moveToTodo();
        this.backlogItem.notifyAssignee();
    }

    public void setDefaultObserversForDiscussion() {
        this.observers.add((Observer) this.creator);
        if (this.backlogItem.getAssignee() != null) {
            this.observers.add((Observer) this.backlogItem.getAssignee());
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
            observer.update(message);
        }
    }

    @Override
    public List<Observer> getObservers() {
        return this.observers;
    }
}
