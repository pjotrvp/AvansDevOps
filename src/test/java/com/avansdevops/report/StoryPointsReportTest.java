package com.avansdevops.report;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.avansdevops.Sprint;
import com.avansdevops.SprintGoal;
import com.avansdevops.backlog.Backlog;
import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.users.Developer;
import com.avansdevops.users.ScrumMaster;
import com.avansdevops.users.User;

public class StoryPointsReportTest {
    private Sprint sprint;
    private StoryPointsReport storyPointsReport;
    private Backlog backlog;
    private BacklogItem item1;
    private BacklogItem item2;
    private User user1;
    private User user2;

    @Before
    public void setUp() {
        backlog = new Backlog("Backlog 1");
        user1 = new Developer("User 1");
        user2 = new ScrumMaster("User 2");
        item1 = new BacklogItem("Item 1", "Description 1", 3);
        item2 = new BacklogItem("Item 2", "Description 2", 5);
        item1.setAssignee(user1);
        item2.setAssignee(user2);
        sprint = new Sprint("Sprint 1", new Date(), new Date(), SprintGoal.PARTIAL_PRODUCT, backlog);
        storyPointsReport = new StoryPointsReport();
    }

    @Test
    public void testGenerateReportInformation() {
        sprint.addParticipant(user1);
        sprint.addParticipant(user2);
        backlog.addBacklogItem(item1);
        backlog.addBacklogItem(item2);
        String expected = "Story Points Report\n-----------\nUser: User 1, Story Points: 3\nUser: User 2, Story Points: 5\n";
        String actual = storyPointsReport.generateReportInformation(sprint);
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerateReportInformationNoBacklogItems() {
        sprint.addParticipant(user1);
        sprint.addParticipant(user2);
        String expected = "Story Points Report\n-----------\n";
        String actual = storyPointsReport.generateReportInformation(sprint);
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerateReportInformationNoParticipantsInSprint() {
        backlog.addBacklogItem(item1);
        backlog.addBacklogItem(item2);

        String expected = "Story Points Report\n-----------\n";
        String actual = storyPointsReport.generateReportInformation(sprint);
        assertEquals(expected, actual);
    }
}
