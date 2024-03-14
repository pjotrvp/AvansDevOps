package com.avansdevops.discussion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.notifications.Observer;
import com.avansdevops.users.Developer;
import com.avansdevops.users.User;
import com.avansdevops.users.UserRole;

public class DiscussionSubjectTest {
    private Discussion discussion;
    private BacklogItem backlogItem;
    private Developer developer;
    private Developer developer1;
    private Developer developer2;

    @Before
    public void setUp() {
        developer1 = Mockito.mock(Developer.class);
        developer2 = Mockito.mock(Developer.class);
        Mockito.when(((User) developer1).getRole()).thenReturn(UserRole.DEVELOPER);
        Mockito.when(((User) developer2).getRole()).thenReturn(UserRole.DEVELOPER);
        backlogItem = new BacklogItem("Test Backlog Item", "Test Description", 1);
        backlogItem.setAssignee(developer2);
        discussion = new Discussion("Test Discussion", "Test Description", developer1);
        discussion.setBacklogItem(backlogItem);
        developer = new Developer("Piet");

    }

    @Test
    public void testSetDefaultObserversForDiscussion() {
        discussion.setDefaultObserversForDiscussion();
        assertTrue(discussion.getObservers().contains((Observer) developer1));
        assertTrue(discussion.getObservers().contains((Observer) developer2));
    }

    @Test
    public void testSetDefaultObserversForDiscussionWithoutAssignee() {
        backlogItem.setAssignee(null);
        discussion.setDefaultObserversForDiscussion();
        assertTrue(discussion.getObservers().contains((Observer) developer1));
        assertFalse(discussion.getObservers().contains((Observer) developer2));
    }

    @Test
    public void testRemoveObserver() {
        discussion.addObserver(developer);
        assertTrue(discussion.getObservers().contains(developer));
        discussion.removeObserver(developer);
        assertFalse(discussion.getObservers().contains(developer));
    }

    @Test
    public void testRemoveObserverNotInList() throws IllegalArgumentException {
        try {
            discussion.removeObserver((Observer) developer);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Observer not found", e.getMessage());
        }
    }

    @Test
    public void testNotifyObservers() {
        Observer observer1 = Mockito.mock(Observer.class);
        Observer observer2 = Mockito.mock(Observer.class);
        discussion.addObserver(observer1);
        discussion.addObserver(observer2);

        String message = "Test message";
        discussion.notifyObservers(UserRole.DEVELOPER, message);

        verify(observer1).update(message);
        verify(observer2).update(message);
    }

    @Test
    public void testObserver() {
        discussion.addObserver(developer);
        assertEquals(1, discussion.getObservers().size());
        assertTrue(discussion.getObservers().contains(developer));
    }

    @Test
    public void testObserverAlreadyExists() throws IllegalArgumentException {
        discussion.addObserver(developer);
        try {
            discussion.addObserver(developer);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Observer already exists", e.getMessage());
        }
    }
}
