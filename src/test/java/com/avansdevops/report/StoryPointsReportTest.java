package com.avansdevops.report;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.avansdevops.Project;
import com.avansdevops.Sprint;
import com.avansdevops.SprintGoal;
import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.users.Developer;
import com.avansdevops.users.ScrumMaster;
import com.avansdevops.users.User;

public class StoryPointsReportTest {
    private Sprint sprint;
    private StoryPointsReport storyPointsReport;
    private BacklogItem item1;
    private BacklogItem item2;
    private User user1;
    private User user2;
    private Project project;

    @Before
    public void setUp() {
        user1 = new Developer("User 1");
        user2 = new ScrumMaster("User 2");
        item1 = new BacklogItem("Item 1", "Description 1", 3);
        item2 = new BacklogItem("Item 2", "Description 2", 5);
        item1.setAssignee(user1);
        item2.setAssignee(user2);
        project = new Project("Project 1");
        project.addMember(user1);
        project.addMember(user2);
        project.addBacklogItem(item1);
        project.addBacklogItem(item2);
        sprint = project.addSprint(new Date(), new Date(), SprintGoal.PARTIAL_PRODUCT);
        storyPointsReport = new StoryPointsReport();

    }

    @Test
    public void testGenerateReportInformation() throws Exception {
        sprint.addParticipant(user1);
        sprint.addParticipant(user2);
        sprint.addBacklogItem(item1);
        sprint.addBacklogItem(item2);
        String expected = "Story Points Report\n-----------\nUser: User 1, Story Points: 3\nUser: User 2, Story Points: 5\n";
        String actual = storyPointsReport.generateReportInformation(sprint);
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerateReportInformationNoBacklogItems() throws Exception {
        sprint.addParticipant(user1);
        sprint.addParticipant(user2);
        String expected = "Story Points Report\n-----------\n";
        String actual = storyPointsReport.generateReportInformation(sprint);
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerateReportInformationNoParticipantsInSprint() throws Exception {
        sprint.addBacklogItem(item1);
        sprint.addBacklogItem(item2);
        String expected = "Story Points Report\n-----------\n";
        String actual = storyPointsReport.generateReportInformation(sprint);
        assertEquals(expected, actual);
    }
}
