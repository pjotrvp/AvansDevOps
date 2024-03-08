package com.avansdevops.backlog;

import org.junit.Before;
import org.junit.Test;

import com.avansdevops.User;

import static org.junit.Assert.*;

public class BacklogItemTest {
    private BacklogItem backlogItem;
    private BacklogItem subTask1;
    private BacklogItem subTask2;
    private User user;

    @Before
    public void setUp() {
        backlogItem = new BacklogItem("title", "description", 1);
        subTask1 = new BacklogItem("subtask1 title", "subtask1 description", 1);
        subTask2 = new BacklogItem("subtask2 title", "subtask2 description", 1);
        user = new User("Piet");
    }

    @Test
    public void testAssignee() {
        backlogItem.setAssignee(user);
        assertEquals(user, backlogItem.getAssignee());
    }

    @Test
    public void testTitle() {
        backlogItem.setTitle("new title");
        assertEquals("new title", backlogItem.getTitle());
    }

    @Test
    public void testDescription() {
        backlogItem.setDescription("new description");
        assertEquals("new description", backlogItem.getDescription());
    }

    @Test
    public void testStoryPoints() {
        backlogItem.setStoryPoints(5);
        assertEquals(5, backlogItem.getStoryPoints());
    }

    @Test
    public void testAddSubTask() {
        backlogItem.addSubTask(subTask1);
        assertTrue(backlogItem.getSubTasks().contains(subTask1));
    }

    @Test
    public void testAddSubTaskToSubTask() {
        backlogItem.addSubTask(subTask1);
        try {
            subTask1.addSubTask(subTask2);
            fail("Expected an IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            assertEquals("A subtask cannot have subtasks", e.getMessage());
        }
    }

    @Test
    public void testMoveToDoneWithSubTasksDone() {
        backlogItem.addSubTask(subTask1);
        backlogItem.setState(new TestedState());
        subTask1.setState(new TestedState());
        subTask1.moveToDone();
        assertTrue(subTask1.getState() instanceof DoneState);
        backlogItem.moveToDone();
        assertTrue(backlogItem.getState() instanceof DoneState);
    }

    @Test
    public void testMoveToDoneWithSubTasksNotDone() {
        backlogItem.addSubTask(subTask1);
        try {
            backlogItem.moveToDone();
            fail("Expected an IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            assertEquals("All subtasks must be Done before the main item can be moved to Done", e.getMessage());
        }
    }
}
