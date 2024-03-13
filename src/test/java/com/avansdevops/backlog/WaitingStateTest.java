package com.avansdevops.backlog;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class WaitingStateTest {
    private BacklogItem backlogItem;

    @Before
    public void setUp() {
        backlogItem = new BacklogItem("title", "description", 1);
    }

    @Test
    public void testMoveToTodo() {
        backlogItem.moveToTodo();
        assertTrue(backlogItem.getState() instanceof TodoState);
    }

    @Test
    public void testMoveToDoing() {
        backlogItem.moveToDoing();
        assertTrue(backlogItem.getState() instanceof WaitingState);
    }

    @Test
    public void testMoveToReadyForTesting() {
        backlogItem.moveToReadyForTesting();
        assertTrue(backlogItem.getState() instanceof WaitingState);
    }

    @Test
    public void testMoveToTesting() {
        backlogItem.moveToTesting();
        assertTrue(backlogItem.getState() instanceof WaitingState);
    }

    @Test
    public void testMoveToTested() {
        backlogItem.moveToTested();
        assertTrue(backlogItem.getState() instanceof WaitingState);
    }

    @Test
    public void testMoveToDone() {
        backlogItem.moveToDone();
        assertTrue(backlogItem.getState() instanceof WaitingState);
    }
}