package com.avansdevops;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.avansdevops.backlog.Backlog;
import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.backlog.DoneState;
import com.avansdevops.backlog.TodoState;
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
            assertEquals("Sprint has already started or finished", e.getMessage());
        }
    }

    @Test
    public void testStartSprintAfterFinish() throws IllegalStateException {
        releaseSprint.startSprint();
        releaseSprint.finishSprint(true);
        try {
            releaseSprint.startSprint();
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Sprint has already started or finished", e.getMessage());
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
            assertEquals("Sprint goal is not partial product", e.getMessage());
        }
    }

    @Test
    public void testFinishSprintBeforeStart() throws IllegalStateException {
        try {
            releaseSprint.finishSprint(true);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Sprint has not started or is already finished",
                    e.getMessage());
        }
    }

    @Test
    public void testFinishSprintAfterFinish() throws IllegalStateException {
        releaseSprint.startSprint();
        releaseSprint.finishSprint(true);
        try {
            releaseSprint.finishSprint(true);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Sprint has not started or is already finished",
                    e.getMessage());
        }
    }

    @Test
    public void testFinishSprint_PartialProductGoal_NoReport() throws IllegalStateException {
        partialProductSprint.startSprint();
        try {
            partialProductSprint.finishSprint(true);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Sprint report is not generated", e.getMessage());
        }
    }

    @Test
    public void testFinishSprint_PartialProductGoal_WithReport() {
        partialProductSprint.startSprint();
        partialProductSprint.generateSprintReport("Test", "Company Name", "Company Logo", 1);
        partialProductSprint.finishSprint(true);
        assertFalse(partialProductSprint.hasStarted());
        assertTrue(partialProductSprint.hasFinished());

    }

    @Test
    public void testFinishSprintSuccessful() {
        releaseSprint.startSprint();
        releaseSprint.finishSprint(true);
        assertTrue(releaseSprint.hasFinished());
        assertFalse(releaseSprint.hasStarted());
    }

    @Test
    public void testFinishSprintUnsuccessful() {
        releaseSprint.startSprint();
        releaseSprint.finishSprint(false);
        assertTrue(releaseSprint.hasFinished());
        assertFalse(releaseSprint.hasStarted());
    }

    @Test
    public void testCommitAndPushDoneItems() {
        Scm mockScm = mock(Scm.class);
        project.setScm(mockScm);
        releaseSprint.addBacklogItem(item);
        releaseSprint.startSprint();
        item.setCode("New Code");
        item.setState(new DoneState());
        releaseSprint.commitAndPushDoneItems();

        verify(mockScm).commit("Test Item is done", "New Code");
        verify(mockScm).push();
    }

    @Test
    public void testCommitAndPushDoneItems_NoDoneItems() {
        Scm mockScm = mock(Scm.class);
        project.setScm(mockScm);
        releaseSprint.addBacklogItem(item);
        releaseSprint.startSprint();
        item.setCode("New Code");
        releaseSprint.commitAndPushDoneItems();

        verify(mockScm, never()).commit(anyString(), anyString());
        verify(mockScm, never()).push();
    }

    @Test
    public void testCommitAndPushDoneItemsCommitPushFail() throws IllegalArgumentException {
        Scm mockScm = mock(Scm.class);
        doThrow(new IllegalArgumentException("Commit/Push failed")).when(mockScm).commit(anyString(), anyString());
        doThrow(new IllegalArgumentException("Commit/Push failed")).when(mockScm).push();
        project.setScm(mockScm);
        releaseSprint.addBacklogItem(item);
        releaseSprint.startSprint();
        item.setState(new DoneState());

        releaseSprint.commitAndPushDoneItems();

        verify(mockScm).commit("Test Item is done", null);
        verify(mockScm).push();
    }

    @Test
    public void testExecuteDevelopmentPipelines_Successful() {
        Project mockProject = mock(Project.class);
        when(mockProject.executePipelines()).thenReturn(true);

        releaseSprint.executeDevelopmentPipelines();

        assertFalse(releaseSprint.hasStarted());
        assertTrue(releaseSprint.hasFinished());
    }

    @Test
    public void testExecuteDevelopmentPipelines_Unsuccessful() throws IllegalStateException {
        Project mockProject = mock(Project.class);
        Sprint mockSprint = new Sprint("Test", new Date(), new Date(), SprintGoal.RELEASE, new Backlog("Test"),
                mockProject);

        try {
            mockSprint.executeDevelopmentPipelines();
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Pipeline execution failed", e.getMessage());
        }
    }

    @Test
    public void testName() {
        releaseSprint.setName("New Name");
        assertEquals("New Name", releaseSprint.getName());
    }

    @Test
    public void testNameAfterStart() {
        releaseSprint.startSprint();
        try {
            releaseSprint.setName("New Name");
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Sprint has already started or finished", e.getMessage());
        }
    }

    @Test
    public void testNameAfterFinish() throws IllegalStateException {
        releaseSprint.startSprint();
        releaseSprint.finishSprint(true);
        try {
            releaseSprint.setName("New Name");
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Sprint has already started or finished", e.getMessage());
        }
    }

    @Test
    public void testStartDate() {
        Date newDate = new Date();
        releaseSprint.setStartDate(newDate);
        assertEquals(newDate, releaseSprint.getStartDate());
    }

    @Test
    public void testStartDateAfterStart() throws IllegalStateException {
        releaseSprint.startSprint();
        try {
            releaseSprint.setStartDate(new Date());
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Sprint has already started or finished", e.getMessage());
        }
    }

    @Test
    public void testStartDateAfterFinish() throws IllegalStateException {
        releaseSprint.startSprint();
        releaseSprint.finishSprint(true);
        try {
            releaseSprint.setStartDate(new Date());
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Sprint has already started or finished", e.getMessage());
        }
    }

    @Test
    public void testEndDate() {
        Date newDate = new Date();
        releaseSprint.setEndDate(newDate);
        assertEquals(newDate, releaseSprint.getEndDate());
    }

    @Test
    public void testEndDateAfterStart() throws IllegalStateException {
        releaseSprint.startSprint();
        try {
            releaseSprint.setEndDate(new Date());
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Sprint has already started or finished", e.getMessage());
        }
    }

    @Test
    public void testEndDateAfterFinish() throws IllegalStateException {
        releaseSprint.startSprint();
        releaseSprint.finishSprint(true);
        try {
            releaseSprint.setEndDate(new Date());
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Sprint has already started or finished", e.getMessage());
        }
    }

    @Test
    public void testGetGoal() {
        assertEquals(SprintGoal.RELEASE, releaseSprint.getGoal());
        assertEquals(SprintGoal.PARTIAL_PRODUCT, partialProductSprint.getGoal());
    }

    @Test
    public void testGetBacklogItems() {
        releaseSprint.addBacklogItem(item);
        assertEquals(1, releaseSprint.getBacklogItems().size());
        assertTrue(releaseSprint.getBacklogItems().contains(item));
    }

    @Test
    public void testGetBacklog() {
        assertNotNull(releaseSprint.getBacklog());
    }

    @Test
    public void testAddBacklogItem() {
        releaseSprint.addBacklogItem(item);
        assertEquals(1, releaseSprint.getBacklogItems().size());
        assertTrue(releaseSprint.getBacklogItems().contains(item));
        assertTrue(item.getState() instanceof TodoState);
    }

    @Test
    public void testAddBacklogItemNotInProject() throws IllegalArgumentException {
        BacklogItem item2 = new BacklogItem("Title", "Description", 4);
        try {
            releaseSprint.addBacklogItem(item2);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Backlog item is not in the project's backlog", e.getMessage());
        }
    }

    @Test
    public void testAddBacklogItemDuplicateItem() throws IllegalArgumentException {
        releaseSprint.addBacklogItem(item);
        try {
            releaseSprint.addBacklogItem(item);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Backlog item is already in the sprint", e.getMessage());
        }
    }

    @Test
    public void testAddBacklogItemAfterStart() throws IllegalStateException {
        releaseSprint.startSprint();
        try {
            releaseSprint.addBacklogItem(item);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Sprint has already started or finished", e.getMessage());
        }
    }

    @Test
    public void testAddBacklogItemAfterFinish() throws IllegalStateException {
        releaseSprint.startSprint();
        releaseSprint.finishSprint(true);
        try {
            releaseSprint.addBacklogItem(item);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Sprint has already started or finished", e.getMessage());
        }
    }

    @Test
    public void testAddBacklogItemAlreadyDone() throws IllegalArgumentException {
        item.setState(new DoneState());
        try {
            releaseSprint.addBacklogItem(item);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Backlog item is already done", e.getMessage());
        }
    }

    @Test
    public void testAddParticipant() {
        releaseSprint.addParticipant(developer);
        assertTrue(releaseSprint.getParticipants().contains(developer));
        assertEquals(1, releaseSprint.getParticipants().size());
        project.addMember(scrumMaster1);
        releaseSprint.startSprint();
        releaseSprint.addParticipant(scrumMaster1);
        assertTrue(releaseSprint.getParticipants().contains(scrumMaster1));
        assertEquals(2, releaseSprint.getParticipants().size());
    }

    @Test
    public void testAddParticipantAfterFinish() throws IllegalStateException {
        releaseSprint.startSprint();
        releaseSprint.finishSprint(true);
        try {
            releaseSprint.addParticipant(developer);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Sprint has already started or finished", e.getMessage());
            assertFalse(releaseSprint.getParticipants().contains(developer));
        }
    }

    @Test
    public void testAddParticipantNotInProject() throws IllegalArgumentException {
        try {
            releaseSprint.addParticipant(scrumMaster2);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("User is not a project member", e.getMessage());
            assertFalse(releaseSprint.getParticipants().contains(scrumMaster2));
        }
    }

    @Test
    public void testAddParticipantAlreadyInSprint() throws IllegalArgumentException {
        releaseSprint.addParticipant(developer);
        try {
            releaseSprint.addParticipant(developer);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("User is already a participant in the sprint", e.getMessage());
            long count = releaseSprint.getParticipants().stream().filter(p -> p.equals(developer)).count();
            assertEquals(1, count);
        }
    }

    @Test
    public void testAddParticipantUserIsProductOwner() throws IllegalArgumentException {
        project.addMember(productOwner);
        try {
            releaseSprint.addParticipant(productOwner);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Sprints don't require a Product Owner", e.getMessage());
            assertFalse(releaseSprint.getParticipants().contains(productOwner));
        }
    }

    @Test
    public void testAddParticipantDoubleScrumMaster() throws IllegalArgumentException {
        project.addMember(scrumMaster1);
        project.addMember(scrumMaster2);
        releaseSprint.addParticipant(scrumMaster1);
        try {
            releaseSprint.addParticipant(scrumMaster2);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Sprint already has a Scrum Master", e.getMessage());
            assertTrue(releaseSprint.getParticipants().contains(scrumMaster1));
            assertFalse(releaseSprint.getParticipants().contains(scrumMaster2));
        }
    }

    @Test
    public void testRemoveParticipant() {
        releaseSprint.addParticipant(developer);
        releaseSprint.removeParticipant(developer);
        assertFalse(releaseSprint.getParticipants().contains(developer));
        assertEquals(0, releaseSprint.getParticipants().size());
    }

    @Test
    public void testRemoveParticipantNonExisiting() throws IllegalArgumentException {
        try {
            releaseSprint.removeParticipant(scrumMaster2);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("User is not a participant in the sprint", e.getMessage());
        }
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get("reports/Test.pdf.txt"));
    }
}