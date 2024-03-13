package com.avansdevops;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ScmTest {
    private Scm git;

    @Before
    public void setUp() {
        git = new Scm("TestScm");
    }

    @Test
    public void testPullIsEmpty() {
        List<String> code = git.pull();
        assertTrue(code.isEmpty());
    }

    @Test
    public void commitWithEmptyTitle() {
        try {
            git.commit("", "new code");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Please provide a commit title", e.getMessage());
        }
    }

    @Test
    public void commitWithEmptyCode() {
        try {
            git.commit("Test commit", "");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Nothing to commit", e.getMessage());
        }
    }

    @Test
    public void commitWithTitleAndCode() {
        git.commit("Test commit", "new code");
        assertEquals("new code", git.getCommitCode());
        assertEquals("Test commit", git.getCommitTitle());
    }

    @Test
    public void pushWithNoCommit() {
        try {
            git.push();
            fail("Expected a NullPointerException to be thrown");
        } catch (NullPointerException e) {
            assertEquals("Nothing to push", e.getMessage());
        }
    }

    @Test
    public void pushWithCommit() {
        git.commit("Test commit", "new code");
        git.push();
        assertTrue(git.pull().contains("new code"));
        assertEquals(null, git.getCommitTitle());
        assertEquals(null, git.getCommitCode());
    }
}