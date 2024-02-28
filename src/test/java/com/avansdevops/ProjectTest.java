package com.avansdevops;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class ProjectTest {
    

    //Tests for requirement 1
    @Test
    public void projectHasBacklog() {
        Project project = new Project("Project 1");
        assertNotNull(project.getBacklog());
    }

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
}
