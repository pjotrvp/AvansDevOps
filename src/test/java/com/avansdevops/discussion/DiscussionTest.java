package com.avansdevops.discussion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.backlog.DoneState;
import com.avansdevops.backlog.TodoState;
import com.avansdevops.users.User;
import com.avansdevops.users.UserFactory;
import com.avansdevops.users.UserRole;

public class DiscussionTest {
    private Discussion discussion;
    private BacklogItem backlogItem;
    private UserFactory factory = new UserFactory();
    private User developer;

    @Before
    public void setUp() {
        developer = factory.createUser(UserRole.DEVELOPER, "Developer");
        discussion = new Discussion("Discussion Title", "Discussion Content", developer);
        backlogItem = new BacklogItem("title", "description", 1);
    }

    @Test
    public void testTitle() {
        discussion.setTitle("New Discussion Title");
        assertEquals("New Discussion Title", discussion.getTitle());
    }

    @Test
    public void testContent() {
        discussion.setContent("New Discussion Content");
        assertEquals("New Discussion Content", discussion.getContent());
    }

    @Test
    public void testCreator() {
        assertEquals(developer, discussion.getCreator());
    }

    @Test
    public void testBacklogItem() {
        discussion.setBacklogItem(backlogItem);
        assertEquals(backlogItem, discussion.getBacklogItem());
    }

    @Test
    public void testMoveBacklogItem() {
        backlogItem.setState(new DoneState());

        discussion.setBacklogItem(backlogItem);
        discussion.moveBacklogItem();
        assertTrue(backlogItem.getState() instanceof TodoState);
    }

    @Test
    public void testMoveBacklogItemNull() {
        try {
            discussion.moveBacklogItem();
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("No backlog item linked to this discussion", e.getMessage());
        }
    }

    @Test
    public void testMoveBacklogItemNotDone() {
        BacklogItem backlogItemNotDone = new BacklogItem("title", "description", 1);
        discussion.setBacklogItem(backlogItemNotDone);
        try {
            discussion.moveBacklogItem();
            fail("Expected an IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            assertEquals("Backlog item must be in the Done state to move it to the Todo state", e.getMessage());
        }
    }

    @Test
    public void testAddResponse() {
        String response = "This is a response";
        discussion.addResponse(response);
        assertTrue(discussion.getResponses().contains(response));
    }

    @Test
    public void testGetResponses() {
        String response1 = "This is the first response";
        String response2 = "This is the second response";
        discussion.addResponse(response1);
        discussion.addResponse(response2);
        List<String> responses = discussion.getResponses();
        assertEquals(2, responses.size());
        assertTrue(responses.contains(response1));
        assertTrue(responses.contains(response2));
    }
}
