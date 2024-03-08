package com.avansdevops.backlog;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestingStateTest {
    private BacklogItem backlogItem;

    @Before
    public void setUp() {
        backlogItem = new BacklogItem("title", "description", 1);
        backlogItem.setState(new TestingState());
    }

    @Test
    public void testMoveToTodo() {
        backlogItem.moveToTodo();
        assertTrue(backlogItem.getState() instanceof TodoState);
    }

    @Test
    public void testMoveToDoing() {
        backlogItem.moveToDoing();
        assertTrue(backlogItem.getState() instanceof TestingState);
    }

    @Test
    public void testMoveToReadyForTesting() {
        backlogItem.moveToReadyForTesting();
        assertTrue(backlogItem.getState() instanceof ReadyForTestingState);
    }

    @Test
    public void testMoveToTesting() {
        backlogItem.moveToTesting();
        assertTrue(backlogItem.getState() instanceof TestingState);
    }

    @Test
    public void testMoveToTested() {
        backlogItem.moveToTested();
        assertTrue(backlogItem.getState() instanceof TestedState);
    }

    @Test
    public void testMoveToDone() {
        backlogItem.moveToDone();
        assertTrue(backlogItem.getState() instanceof TestingState);
    }
}
