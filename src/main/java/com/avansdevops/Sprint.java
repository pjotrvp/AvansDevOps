package com.avansdevops;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avansdevops.backlog.Backlog;
import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.users.User;

public class Sprint {
    private String name;
    private Date startDate;
    private Date endDate;
    private SprintGoal goal;
    private Backlog backlog;
    private boolean started;
    private List<User> participants = new ArrayList<>();

    private static final String SPRINT_ALREADY_STARTED_ERROR = "Sprint already started";

    public Sprint(String name,
            Date startDate,
            Date endDate,
            SprintGoal goal,
            Backlog backlog) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.goal = goal;
        this.backlog = backlog;
        this.started = false;
        // check if startDate is same as today or in the past
        if (startDate.before(new Date())) {
            this.started = true;
        }
    }

    public void addParticipant(User user) {
        if (!participants.contains(user)) {
            participants.add(user);
        }
    }

    public List<User> getParticipants() {
        return participants;
    }

    public List<BacklogItem> getBacklog() {
        return this.backlog.getBacklogItems();
    }

    public void start() {
        this.started = true;
        // start the sprint
    }

    public boolean isStarted() {
        return this.started;
    }

    public void setName(String name) throws IllegalStateException {
        if (!started) {
            this.name = name;
        } else {
            throw new IllegalStateException(SPRINT_ALREADY_STARTED_ERROR);
        }
    }

    public void setGoal(SprintGoal goal) {
        if (!started) {
            this.goal = goal;
        } else {
            throw new IllegalStateException(SPRINT_ALREADY_STARTED_ERROR);
        }
    }

    public void setStartDate(Date startDate) {
        if (!started) {
            this.startDate = startDate;
        } else {
            throw new IllegalStateException(SPRINT_ALREADY_STARTED_ERROR);
        }
    }

    public void setEndDate(Date endDate) {
        if (!started) {
            this.endDate = endDate;
        } else {
            throw new IllegalStateException(SPRINT_ALREADY_STARTED_ERROR);
        }
    }

    public Object getName() {
        return this.name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public SprintGoal getGoal() {
        return goal;
    }
}
