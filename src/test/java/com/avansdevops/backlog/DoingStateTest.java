package com.avansdevops.backlog;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DoingStateTest {
    private BacklogItem backlogItem;

    @Before
    public void setUp() {
        backlogItem = new BacklogItem("title", "description", 1);
        backlogItem.setState(new DoingState());
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
        assertTrue(backlogItem.getState() instanceof ReadyForTestingState);
    }

    @Test
    public void testMoveToTesting() {
        backlogItem.moveToTesting();
        assertTrue(backlogItem.getState() instanceof DoingState);
    }

    @Test
    public void testMoveToTested() {
        backlogItem.moveToTested();
        assertTrue(backlogItem.getState() instanceof DoingState);
    }

    @Test
    public void testMoveToDone() {
        backlogItem.moveToDone();
        assertTrue(backlogItem.getState() instanceof DoingState);
    }
}