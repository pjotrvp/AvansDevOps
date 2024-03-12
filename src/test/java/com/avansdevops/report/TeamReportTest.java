package com.avansdevops.report;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.avansdevops.Sprint;
import com.avansdevops.SprintGoal;
import com.avansdevops.backlog.Backlog;
import com.avansdevops.users.Developer;
import com.avansdevops.users.ScrumMaster;
import com.avansdevops.users.User;

public class TeamReportTest {
    private Sprint sprint;
    private TeamReport teamReport;
    private User user1;
    private User user2;

    @Before
    public void setUp() {
        sprint = new Sprint("Sprint 1", new Date(), new Date(), SprintGoal.PARTIAL_PRODUCT, new Backlog("Backlog 1"));
        user1 = new Developer("User 1");
        user2 = new ScrumMaster("User 2");
        teamReport = new TeamReport();
    }

    @Test
    public void testGenerateReportInformation() {
        sprint.addParticipant(user1);
        sprint.addParticipant(user2);
        String expected = "Team Report\n-----------\nParticipants:\n- User 1 (DEVELOPER)\n- User 2 (SCRUMMASTER)\n";
        String actual = teamReport.generateReportInformation(sprint);
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerateReportInformationNoParticipants() {
        String expected = "Team Report\n-----------\nParticipants:\n";
        String actual = teamReport.generateReportInformation(sprint);
        assertEquals(expected, actual);
    }
}