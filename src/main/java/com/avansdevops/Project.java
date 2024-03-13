package com.avansdevops;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avansdevops.backlog.Backlog;
import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.discussion.Forum;
import com.avansdevops.pipeline.AnalysisPipeline;
import com.avansdevops.pipeline.BuildPipeline;
import com.avansdevops.pipeline.DeployPipeline;
import com.avansdevops.pipeline.PipelineTemplate;
import com.avansdevops.pipeline.TestPipeline;
import com.avansdevops.users.ProductOwner;
import com.avansdevops.users.User;

public class Project {
    private String name;
    private Backlog backlog;
    private Forum forum;
    private List<User> members = new ArrayList<>();
    private List<PipelineTemplate> pipelines = new ArrayList<>();
    private List<Sprint> sprints = new ArrayList<>();
    private Scm scm;

    public Project(String name) {
        this.name = name;
        this.backlog = new Backlog("Project Backlog");
        this.forum = new Forum("Discussion Forum");
        this.pipelines.add(new BuildPipeline("Build"));
        this.pipelines.add(new TestPipeline("Test"));
        this.pipelines.add(new AnalysisPipeline("Analysis"));
        this.pipelines.add(new DeployPipeline("Deploy"));
    }

    public String getName() {
        return this.name;
    }

    public List<BacklogItem> getBacklogItems() {
        return this.backlog.getBacklogItems();
    }

    public void addBacklogItem(BacklogItem item) {
        if (!getBacklogItems().contains(item)) {
            this.backlog.addBacklogItem(item);
        }
    }

    public Backlog getBacklog() {
        return this.backlog;
    }

    public Forum getDiscussionForum() {
        return this.forum;
    }

    public List<User> getMembers() {
        return this.members;
    }

    public void addMember(User user) throws IllegalArgumentException {
        if (this.members.contains(user)) {
            throw new IllegalArgumentException("User is already a member of the project");
        }

        if (user instanceof ProductOwner && this.members.stream().anyMatch(p -> p instanceof ProductOwner)) {

            throw new IllegalArgumentException("Project already has a Product Owner");
        }

        this.members.add(user);
    }

    public void removeMember(User user) throws IllegalArgumentException {
        if (!this.members.contains(user)) {
            throw new IllegalArgumentException("User is not a member of the project");
        }

        this.members.remove(user);

        for (Sprint sprint : this.sprints) {
            if (sprint.getParticipants().contains(user)) {
                sprint.removeParticipant(user);
            }
        }
    }

    public List<PipelineTemplate> getPipelines() {
        return this.pipelines;
    }

    public boolean executePipelines() {
        for (PipelineTemplate pipeline : this.pipelines) {
            if (!pipeline.executePipeline()) {
                return false;
            }
        }
        return true;
    }

    public Sprint addSprint(Date startDate, Date endDate, SprintGoal goal) {
        Sprint sprint = new Sprint("Sprint " + (this.sprints.size() + 1), startDate, endDate, goal,
                new Backlog("Sprint Backlog" + (this.sprints.size() + 1)), this);
        this.sprints.add(sprint);
        return sprint;
    }

    public List<Sprint> getSprints() {
        return this.sprints;
    }

    public Scm getScm() {
        return this.scm;
    }

    public void setScm(Scm scm) {
        this.scm = scm;
    }
}
