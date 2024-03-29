package com.avansdevops.backlog;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TodoStateTest {
    private BacklogItem backlogItem;

    @Before
    public void setUp() {
        backlogItem = new BacklogItem("title", "description", 1);
        backlogItem.setState(new TodoState());
    }

    @Test
    public void testMoveToTodo() {
        backlogItem.moveToTodo();
        assertTrue(backlogItem.getState() instanceof TodoState);
    }

    @Test
    public void testMoveToDoing() {
        backlogItem.moveToDoing();
        assertTrue(backlogItem.getState() instanceof DoingState);
    }

    @Test
    public void testMoveToReadyForTesting() {
        backlogItem.moveToReadyForTesting();
        assertTrue(backlogItem.getState() instanceof TodoState);
    }

    @Test
    public void testMoveToTesting() {
        backlogItem.moveToTesting();
        assertTrue(backlogItem.getState() instanceof TodoState);
    }

    @Test
    public void testMoveToTested() {
        backlogItem.moveToTested();
        assertTrue(backlogItem.getState() instanceof TodoState);
    }

    @Test
    public void testMoveToDone() {
        backlogItem.moveToDone();
        assertTrue(backlogItem.getState() instanceof TodoState);
    }
}