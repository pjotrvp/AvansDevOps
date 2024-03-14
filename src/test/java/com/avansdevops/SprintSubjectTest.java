package com.avansdevops;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.notifications.Observer;
import com.avansdevops.users.Developer;
import com.avansdevops.users.ScrumMaster;
import com.avansdevops.users.Tester;
import com.avansdevops.users.User;
import com.avansdevops.users.UserFactory;
import com.avansdevops.users.UserRole;

public class SprintSubjectTest {
    private Project project;
    private Sprint sprint;
    private UserFactory userFactory = new UserFactory();
    private Observer developer;
    private Observer scrumMaster;
    private Observer tester;
    private Observer productOwner;
    private Tester mockTester;
    private Developer mockDeveloper;
    private ScrumMaster mockScrumMaster;
    private BacklogItem backlogItem;

    @Before
    public void setUp() {
        project = new Project("Test Project");
        sprint = project.addSprint(new Date(), new Date(), SprintGoal.RELEASE);
        backlogItem = new BacklogItem("Title", "Description", 1);
        project.addBacklogItem(backlogItem);
        sprint.addBacklogItem(backlogItem);
        developer = (Observer) userFactory.createUser(UserRole.DEVELOPER, "Piet");
        scrumMaster = (Observer) userFactory.createUser(UserRole.SCRUM_MASTER, "Klaas");
        tester = (Observer) userFactory.createUser(UserRole.TESTER, "Jan");
        productOwner = (Observer) userFactory.createUser(UserRole.PRODUCT_OWNER, "Henk");
        project.addMember((User) developer);
        project.addMember((User) scrumMaster);
        project.addMember((User) tester);
        mockTester = Mockito.mock(Tester.class);
        mockDeveloper = Mockito.mock(Developer.class);
        mockScrumMaster = Mockito.mock(ScrumMaster.class);
        Mockito.when(((User) mockTester).getRole()).thenReturn(UserRole.TESTER);
        Mockito.when(((User) mockDeveloper).getRole()).thenReturn(UserRole.DEVELOPER);
        Mockito.when(((User) mockScrumMaster).getRole()).thenReturn(UserRole.SCRUM_MASTER);
        backlogItem.setAssignee((User) developer);
    }

    @Test
    public void testSetDefaultObserversForBacklogItemsWithTesterScrumMasterAndDeveloperAssigned() {
        sprint.addParticipant((User) scrumMaster);
        sprint.addParticipant((User) tester);
        sprint.addParticipant((User) developer);
        backlogItem.setAssignee((User) developer);
        sprint.setDefaultObserversForBacklogItems();
        assertTrue(backlogItem.getObservers().contains((Observer) scrumMaster));
        assertTrue(backlogItem.getObservers().contains((Observer) tester));
        assertTrue(backlogItem.getObservers().contains((Observer) developer));
    }

    @Test
    public void testSetDefaultObserversForBacklogItemsWithTesterScrumMasterAndDeveloperNotAssigned() {
        sprint.addParticipant((User) scrumMaster);
        sprint.addParticipant((User) tester);
        sprint.addParticipant((User) developer);
        backlogItem.setAssignee(null);
        sprint.setDefaultObserversForBacklogItems();
        assertTrue(backlogItem.getObservers().contains((Observer) scrumMaster));
        assertTrue(backlogItem.getObservers().contains((Observer) tester));
        assertFalse(backlogItem.getObservers().contains((Observer) developer));
    }

    @Test
    public void testSetDefaultObserversForSprint() {
        project.addMember((User) productOwner);
        sprint.addParticipant((User) scrumMaster);
        assertEquals(0, sprint.getObservers().size());
        sprint.setDefaultObserversForSprint();
        assertTrue(sprint.getObservers().contains((Observer) scrumMaster));
        assertTrue(sprint.getObservers().contains((Observer) productOwner));
        assertEquals(2, sprint.getObservers().size());
    }

    @Test
    public void testSetDefaultObserversForSprintNoScrumMaster() {
        project.addMember((User) productOwner);
        assertEquals(0, sprint.getObservers().size());
        sprint.setDefaultObserversForSprint();
        assertFalse(sprint.getObservers().contains((Observer) scrumMaster));
        assertTrue(sprint.getObservers().contains((Observer) productOwner));
        assertEquals(1, sprint.getObservers().size());
    }

    @Test
    public void testSetDefaultObserversForSprintNoProductOwner() {
        sprint.addParticipant((User) scrumMaster);
        assertEquals(0, sprint.getObservers().size());
        sprint.setDefaultObserversForSprint();
        assertFalse(sprint.getObservers().contains((Observer) productOwner));
        assertEquals(1, sprint.getObservers().size());
    }

    @Test
    public void testRemoveObserver() {
        sprint.addObserver(developer);
        sprint.removeObserver(developer);
        assertFalse(sprint.getObservers().contains(developer));
    }

    @Test
    public void testRemoveObserverNotInList() throws IllegalArgumentException {
        try {
            sprint.removeObserver((Observer) scrumMaster);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Observer not found", e.getMessage());
        }
    }

    @Test
    public void testNotifyObservers() {
        sprint.addObserver(mockScrumMaster);
        sprint.addObserver(mockDeveloper);

        String message = "Test message";
        sprint.notifyObservers(UserRole.SCRUM_MASTER, message);

        verify(mockScrumMaster).update(message);
        verify(mockDeveloper, Mockito.never()).update(message);
    }

    @Test
    public void testObserver() {
        sprint.addObserver(scrumMaster);
        assertEquals(1, sprint.getObservers().size());
        assertTrue(sprint.getObservers().contains(scrumMaster));
    }

    @Test
    public void testObserverAlreadyExists() throws IllegalArgumentException {
        sprint.addObserver(scrumMaster);
        try {
            sprint.addObserver(scrumMaster);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Observer already exists", e.getMessage());
        }
    }
}
