package com.avansdevops;

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
    }
}
