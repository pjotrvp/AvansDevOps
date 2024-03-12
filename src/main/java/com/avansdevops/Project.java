package com.avansdevops;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.backlog.Backlog;
import com.avansdevops.discussion.Forum;
import com.avansdevops.users.Developer;
import com.avansdevops.users.ProductOwner;
import com.avansdevops.users.ScrumMaster;
import com.avansdevops.users.Tester;
import com.avansdevops.users.User;

public class Project {
    private Backlog backlog;
    private String name;
    private List<User> members;
    private Forum discussionForum;
    private List<Sprint> sprints;

    public Project(String name) {
        this.name = name;
        this.backlog = new Backlog("Sprint 1");
        this.discussionForum = new Forum("Discussion Forum");
        this.sprints = new ArrayList<>();
    }

    public Backlog getBacklog() {
        return this.backlog;
    }

    public void addSprint(Sprint newSprint) {
        this.sprints.add(newSprint);
    }

    public void createDeveloper(String name)
    {
        this.members.add(new Developer(name));  
    }

    public void createProductOwner(String name)
    {
        this.members.add(new ProductOwner(name));
    }

    public void createScrumMaster(String name)
    {
        this.members.add(new ScrumMaster(name));
    }

    public void createTester(String name)
    {
        this.members.add(new Tester(name));
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
