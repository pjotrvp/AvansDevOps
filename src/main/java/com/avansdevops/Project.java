package com.avansdevops;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.backlog.Backlog;
import com.avansdevops.discussion.Forum;
import com.avansdevops.users.User;
import com.avansdevops.users.UserFactory;
import com.avansdevops.users.UserRole;

public class Project {
    private Backlog backlog;
    private String name;
    private List<User> members;
    private Forum discussionForum;
    private List<Sprint> sprints;
    private UserFactory userFactory = new UserFactory();

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

    public void addMember(UserRole role, String name) {
        User user = userFactory.createUser(role, name);
        this.members.add(user);
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
