package com.avansdevops.report;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.avansdevops.Project;
import com.avansdevops.Sprint;
import com.avansdevops.SprintGoal;
import com.avansdevops.backlog.BacklogItem;

public class BurndownReportTest {
    private Sprint sprint;
    private BurndownReport burndownReport;
    private BacklogItem item1;
    private BacklogItem item2;
    private Project project;

    @Before
    public void setUp() {
        item1 = new BacklogItem("Item 1", "Description 1", 3);
        item2 = new BacklogItem("Item 2", "Description 2", 5);
        project = new Project("Project 1");
        sprint = project.addSprint(new Date(), new Date(), SprintGoal.PARTIAL_PRODUCT);
        burndownReport = new BurndownReport();
        project.addBacklogItem(item1);
        project.addBacklogItem(item2);
    }

    @Test
    public void testGenerateReportInformation() throws Exception {
        sprint.addBacklogItem(item1);
        sprint.addBacklogItem(item2);
        String expected = "Burndown Chart\n--------------\nTotal Story Points: 8\nDay\tRemaining Story Points\n1\t8\n2\t5\n3\t0\n";
        String actual = burndownReport.generateReportInformation(sprint);
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerateReportInformation_NoBacklogItems() {
        String expected = "Burndown Chart\n--------------\nTotal Story Points: 0\nDay\tRemaining Story Points\n1\t0\n";
        String actual = burndownReport.generateReportInformation(sprint);
        assertEquals(expected, actual);
    }
}
