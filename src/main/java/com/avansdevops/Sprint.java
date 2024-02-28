package com.avansdevops;

import java.util.Date;

public class Sprint {
    private String name;
    private Date startDate;
    private Date endDate;
    private SprintGoal goal;
    private Backlog backlog;
    private boolean started;

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

    public void start() {
        this.started = true;
        // start the sprint
    }

    public boolean isStarted() {
        return this.started;
    }

    public void setName(String name) {
        if (!started) {
            this.name = name;
        } else {
            throw new IllegalStateException("Sprint already started");
        }
    }

    public void setGoal(SprintGoal goal) {
        if (!started) {
            this.goal = goal;
        } else {
            throw new IllegalStateException("Sprint already started");
        }
    }

    public void setStartDate(Date startDate) {
        if (!started) {
            this.startDate = startDate;
        } else {
            throw new IllegalStateException("Sprint already started");
        }
    }

    public void setEndDate(Date endDate) {
        if (!started) {
            this.endDate = endDate;
        } else {
            throw new IllegalStateException("Sprint already started");
        }
    }

    public Object getName() {
        return this.name;
    }

}
