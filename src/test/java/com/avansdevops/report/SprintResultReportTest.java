package com.avansdevops.report;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.avansdevops.Project;
import com.avansdevops.Sprint;
import com.avansdevops.SprintGoal;
import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.backlog.DoneState;

public class SprintResultReportTest {
    private Sprint sprint;
    private SprintResultReport sprintResultReport;
    private BacklogItem item1;
    private BacklogItem item2;
    private Project project;

    @Before
    public void setUp() {
        item1 = new BacklogItem("Item 1", "Description 1", 3);
        item2 = new BacklogItem("Item 2", "Description 2", 5);
        project = new Project("Project 1");
        sprint = project.addSprint(new Date(), new Date(), SprintGoal.PARTIAL_PRODUCT);
        sprintResultReport = new SprintResultReport();
        project.addBacklogItem(item1);
        project.addBacklogItem(item2);
    }

    @Test
    public void testGenerateReportInformation() throws Exception {
        sprint.addBacklogItem(item1);
        sprint.addBacklogItem(item2);
        item1.setState(new DoneState());
        item2.setState(new DoneState());
        String expected = "Sprint Result - Partial Product\n--------------\n\nDone Backlog Items:\n- Item 1\n- Item 2\n";
        String actual = sprintResultReport.generateReportInformation(sprint);
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerateReportInformation_NoBacklogItems() {
        String expected = "Sprint Result - Partial Product\n--------------\n\nDone Backlog Items:\n";
        String actual = sprintResultReport.generateReportInformation(sprint);
        assertEquals(expected, actual);
    }
}
