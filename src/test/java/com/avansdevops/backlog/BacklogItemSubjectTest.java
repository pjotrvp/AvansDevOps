package com.avansdevops.backlog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.avansdevops.notifications.Observer;
import com.avansdevops.users.Developer;
import com.avansdevops.users.ScrumMaster;
import com.avansdevops.users.Tester;
import com.avansdevops.users.User;
import com.avansdevops.users.UserFactory;
import com.avansdevops.users.UserRole;

public class BacklogItemSubjectTest {
    private BacklogItem backlogItem;
    private UserFactory userFactory = new UserFactory();
    private Observer developer;
    private Observer scrumMaster;
    private Observer tester;
    private Tester mockTester;
    private Developer mockDeveloper;
    private ScrumMaster mockScrumMaster;

    @Before
    public void setUp() {
        backlogItem = new BacklogItem("Title", "Description", 1);
        backlogItem.setState(new TodoState());
        developer = (Observer) userFactory.createUser(UserRole.DEVELOPER, "Piet");
        scrumMaster = (Observer) userFactory.createUser(UserRole.SCRUM_MASTER, "Klaas");
        tester = (Observer) userFactory.createUser(UserRole.TESTER, "Jan");
        mockTester = Mockito.mock(Tester.class);
        mockDeveloper = Mockito.mock(Developer.class);
        mockScrumMaster = Mockito.mock(ScrumMaster.class);
        Mockito.when(((User) mockTester).getRole()).thenReturn(UserRole.TESTER);
        Mockito.when(((User) mockDeveloper).getRole()).thenReturn(UserRole.DEVELOPER);
        Mockito.when(((User) mockScrumMaster).getRole()).thenReturn(UserRole.SCRUM_MASTER);
        backlogItem.addObserver(developer);
        backlogItem.addObserver(scrumMaster);
        backlogItem.addObserver(tester);

    }

    @Test
    public void testGetObservers() {
        assertEquals(3, backlogItem.getObservers().size());
        assertTrue(backlogItem.getObservers().contains(developer));
        assertTrue(backlogItem.getObservers().contains(scrumMaster));
        assertTrue(backlogItem.getObservers().contains(tester));
    }

    @Test
    public void testNotifyTesters() {
        backlogItem.addObserver((Observer) mockTester);
        backlogItem.setState(new ReadyForTestingState());
        backlogItem.notifyTesters();
        verify((Observer) mockTester).update("Backlog item " + backlogItem.getTitle() + " is ready for testing");
    }

    @Test
    public void testNotifyTestersNotInReadyForTestingState() {
        backlogItem.addObserver((Observer) mockTester);
        backlogItem.setState(new TodoState());
        backlogItem.notifyTesters();
        verify((Observer) mockTester, Mockito.never())
                .update("Backlog item " + backlogItem.getTitle() + " is ready for testing");
    }

    @Test
    public void testNotifyScrumMaster() {
        backlogItem.addObserver((Observer) mockScrumMaster);
        backlogItem.setState(new ReadyForTestingState());
        backlogItem.notifyScrumMaster();
        verify((Observer) mockScrumMaster)
                .update("Backlog item " + backlogItem.getTitle() + " has been moved to " + backlogItem.getState());
    }

    @Test
    public void testNotifyAssigneeInTodoState() {
        backlogItem.setAssignee(mockDeveloper);
        backlogItem.setState(new TodoState());
        backlogItem.notifyAssignee();
        verify((Observer) mockDeveloper)
                .update("Backlog item " + backlogItem.getTitle() + " has been moved back to " + backlogItem.getState());
    }

    @Test
    public void testNotifyAssigneeNotInTodoState() {
        backlogItem.setAssignee(mockDeveloper);
        backlogItem.setState(new ReadyForTestingState());
        backlogItem.notifyAssignee();
        verify((Observer) mockDeveloper, Mockito.never())
                .update("Backlog item " + backlogItem.getTitle() + " has been moved back to " + backlogItem.getState());
    }

    @Test
    public void testNotifyAssigneeNoAssignee() {
        backlogItem.setState(new TodoState());
        backlogItem.notifyAssignee();
        verify((Observer) mockDeveloper, Mockito.never())
                .update("Backlog item " + backlogItem.getTitle() + " has been moved back to " + backlogItem.getState());
    }

    @Test
    public void testRemoveObserver() {
        assertTrue(backlogItem.getObservers().contains(developer));
        backlogItem.removeObserver(developer);
        assertFalse(backlogItem.getObservers().contains(developer));
    }

    @Test
    public void testRemoveObserverNotInList() throws IllegalArgumentException {
        try {
            backlogItem.removeObserver((Observer) mockDeveloper);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Observer not found", e.getMessage());
        }
    }

    @Test
    public void testObserver() {
        backlogItem.addObserver(mockDeveloper);
        assertEquals(4, backlogItem.getObservers().size());
        assertTrue(backlogItem.getObservers().contains(mockDeveloper));
    }

    @Test
    public void testObserverAlreadyExists() throws IllegalArgumentException {
        try {
            backlogItem.addObserver(developer);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Observer already exists", e.getMessage());
        }
    }
}