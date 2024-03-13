package com.avansdevops.report;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.avansdevops.Project;
import com.avansdevops.Sprint;
import com.avansdevops.SprintGoal;
import com.avansdevops.users.Developer;
import com.avansdevops.users.ScrumMaster;
import com.avansdevops.users.User;

public class TeamReportTest {
    private Sprint sprint;
    private TeamReport teamReport;
    private User user1;
    private User user2;
    private Project project;

    @Before
    public void setUp() {
        project = new Project("Project 1");
        sprint = project.addSprint(new Date(), new Date(), SprintGoal.PARTIAL_PRODUCT);
        user1 = new Developer("User 1");
        user2 = new ScrumMaster("User 2");
        project.addMember(user1);
        project.addMember(user2);
        teamReport = new TeamReport();
    }

    @Test
    public void testGenerateReportInformation() throws Exception {
        sprint.addParticipant(user1);
        sprint.addParticipant(user2);
        String expected = "Team Report\n-----------\nParticipants:\n- User 1 (DEVELOPER)\n- User 2 (SCRUM_MASTER)\n";
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