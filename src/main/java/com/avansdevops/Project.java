package com.avansdevops;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.backlog.Backlog;
import com.avansdevops.discussion.Forum;

public class Project {
    private Backlog backlog;
    private String name;
    private List<User> members;
    private Forum discussionForum;
    private List<Sprint> sprints;

    public Project(String name) {
        this.name = name;
        this.backlog = new Backlog("Sprint 1");
        this.discussionForum = new Forum();
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

    public List<User> getMembers() {
        return this.members;
    }

    public Forum getDiscussionForum() {
        return this.discussionForum;
    }



    @Override
    public String toString() {
        return "Project: " + this.name;
    
    }

    public Scm getScm() {
        return new Scm("Git", "System.out.println(\"Hello World\");");
    }
}
