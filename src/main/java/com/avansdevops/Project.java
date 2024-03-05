package com.avansdevops;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private Backlog backlog;
    private String name;
    private List<User> members;
    private DiscussionForum discussionForum;
    private List<Sprint> sprints;

    public Project(String name) {
        this.name = name;
        this.backlog = new Backlog();
        this.discussionForum = new DiscussionForum();
        this.sprints = new ArrayList<>();
    }

    public Backlog getBacklog() {
        return this.backlog;
    }

    public void addSprint(Sprint newSprint) {
        this.sprints.add(newSprint);
    }

    public List<Sprint> getSprints() {
        return this.sprints;
    }

    public DiscussionForum getDiscussionForum() {
        return this.discussionForum;
    }

    public Scm getScm() {
        return new Scm("GitLab");
    }
}
