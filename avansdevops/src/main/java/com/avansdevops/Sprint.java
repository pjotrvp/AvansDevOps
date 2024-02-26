package com.avansdevops;

import java.util.Date;

public class Sprint {
    private String name;
    private Date startDate;
    private Date endDate;
    private SprintGoal goal;
    private Backlog backlog;

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
    }
}
