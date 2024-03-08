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
        backlogItem.getState().moveToTodo(backlogItem);
        assertTrue(backlogItem.getState() instanceof TodoState);
    }

    @Test
    public void testMoveToDoing() {
        backlogItem.getState().moveToDoing(backlogItem);
        assertTrue(backlogItem.getState() instanceof TestingState);
    }

    @Test
    public void testMoveToReadyForTesting() {
        backlogItem.getState().moveToReadyForTesting(backlogItem);
        assertTrue(backlogItem.getState() instanceof ReadyForTestingState);
    }

    @Test
    public void testMoveToTesting() {
        backlogItem.getState().moveToTesting(backlogItem);
        assertTrue(backlogItem.getState() instanceof TestingState);
    }

    @Test
    public void testMoveToTested() {
        backlogItem.getState().moveToTested(backlogItem);
        assertTrue(backlogItem.getState() instanceof TestedState);
    }

    @Test
    public void testMoveToDone() {
        backlogItem.getState().moveToDone(backlogItem);
        assertTrue(backlogItem.getState() instanceof TestingState);
    }
}
