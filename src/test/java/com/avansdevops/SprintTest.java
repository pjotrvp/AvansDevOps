package com.avansdevops;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.users.User;
import com.avansdevops.users.UserFactory;
import com.avansdevops.users.UserRole;

public class SprintTest {
    private Project project;
    private Sprint releaseSprint;
    private Sprint partialProductSprint;
    private BacklogItem item;
    private UserFactory userFactory = new UserFactory();
    private User developer;
    private User scrumMaster1;
    private User scrumMaster2;
    private User productOwner;

    @Before
    public void setUp() {
        project = new Project("Test Project");
        releaseSprint = project.addSprint(new Date(), new Date(), SprintGoal.RELEASE);
        partialProductSprint = project.addSprint(new Date(), new Date(), SprintGoal.PARTIAL_PRODUCT);
        developer = userFactory.createUser(UserRole.DEVELOPER, "Developer");
        scrumMaster1 = userFactory.createUser(UserRole.SCRUM_MASTER, "Scrum Master 1");
        scrumMaster2 = userFactory.createUser(UserRole.SCRUM_MASTER, "Scrum Master 2");
        productOwner = userFactory.createUser(UserRole.PRODUCT_OWNER, "Product Owner");
        item = new BacklogItem("Test Item", "Test Description", 5);
        project.addBacklogItem(item);
        project.addMember(developer);
    }

    @Test
    public void testStartSprint() {
        releaseSprint.startSprint();
        assertTrue(releaseSprint.hasStarted());
    }

    @Test
    public void testStartSprintAfterStart() throws IllegalStateException {
        releaseSprint.startSprint();
        try {
            releaseSprint.startSprint();
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().equals("Sprint has already started or finished"));
        }
    }

    @Test
    public void testStartSprintAfterFinish() throws Exception {
        releaseSprint.startSprint();
        releaseSprint.finishSprint(true);
        try {
            releaseSprint.startSprint();
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().equals("Sprint has already started or finished"));
        }
    }

    @Test
    public void testGenerateSprintReport() {
        partialProductSprint.startSprint();
        partialProductSprint.generateSprintReport("Test", "Company Name", "Company Logo", 1);
        assertTrue(Files.exists(Paths.get("reports/Test.pdf.txt")));
        assertNotNull(partialProductSprint.getReport());
    }

    @Test
    public void testGenerateSprintReportWithoutPartialProductGoal() {
        releaseSprint.startSprint();
        try {
            releaseSprint.generateSprintReport("Test", "Company Name", "Company Logo", 1);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().equals("Sprint goal is not partial product"));
        }
    }

    @Test
    public void testFinishSprintUnsuccessful() throws Exception {
        releaseSprint.startSprint();
        releaseSprint.finishSprint(false);
        assertTrue(releaseSprint.hasFinished());
        assertFalse(releaseSprint.hasStarted());
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get("reports/Test.pdf.txt"));
    }
}