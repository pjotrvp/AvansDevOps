package com.avansdevops;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class SprintTest {
    
    //requirement 2
    @Test
    public void projectCanMakeSprint() {
        Project project = new Project("Project 1");
        //create dates
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, 10, 10);
        Date startDate = calendar.getTime();
        calendar.set(2024, 10, 20);
        Date endDate = calendar.getTime();

        //add the sprint
        Sprint sprint = new Sprint("Anti flickering feature", startDate, endDate, SprintGoal.PARTIALPRODUCT, project.getBacklog());
        project.addSprint(sprint);
        assertNotNull(project.getSprints());
    }

    @Test
    public void sprintCantBeEditedAfterStart() 
    {
        Project project = new Project("Project 1");
        //create dates
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, 10, 10);
        Date startDate = calendar.getTime();
        calendar.set(2024, 10, 20);
        Date endDate = calendar.getTime();

        //add the sprint
        Sprint sprint = new Sprint("Anti flickering feature", startDate, endDate, SprintGoal.PARTIALPRODUCT, project.getBacklog());
        project.addSprint(sprint);
        sprint.start();
        try {
            sprint.setName("Anti jumping feature");
        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "Sprint already started");
        }
        assertEquals(sprint.getName(), "Anti flickering feature");
    }

    @Test
    public void sprintCanBeEditedBeforeStart()
    {
        Project project = new Project("Project 1");
        //create dates
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, 10, 10);
        Date startDate = calendar.getTime();
        calendar.set(2024, 10, 20);
        Date endDate = calendar.getTime();

        //add the sprint
        Sprint sprint = new Sprint("Anti flickering feature", startDate, endDate, SprintGoal.PARTIALPRODUCT, project.getBacklog());
        project.addSprint(sprint);
        sprint.setName("Anti jumping feature");
        assertEquals(sprint.getName(), "Anti jumping feature");
    }

    @Test
    public void sprintIsAutoStartedWhenStartDateIsInPastOrToday()
    {
        Project project = new Project("Project 1");
        //create dates
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 10, 10);
        Date startDate = calendar.getTime();
        calendar.set(2018, 10, 20);
        Date endDate = calendar.getTime();

        //add the sprint
        Sprint sprint = new Sprint("Anti flickering feature", startDate, endDate, SprintGoal.PARTIALPRODUCT, project.getBacklog());
        project.addSprint(sprint);
        assertEquals(sprint.isStarted(), true);
    }

    @Test
    public void sprintIsNotAutoStartedWhenStartDateIsInFuture()
    {
        Project project = new Project("Project 1");
        //create dates
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, 10, 10);
        Date startDate = calendar.getTime();
        calendar.set(2024, 10, 20);
        Date endDate = calendar.getTime();

        //add the sprint
        Sprint sprint = new Sprint("Anti flickering feature", startDate, endDate, SprintGoal.PARTIALPRODUCT, project.getBacklog());
        project.addSprint(sprint);
        assertEquals(sprint.isStarted(), false);
    }
}
