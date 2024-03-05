package com.avansdevops;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;

public class ScmTest {
    private Scm git;

    @Before
    public void setUp() {
        git = new Scm("TestScm", "System.out.println(\"Hello, World!\");");
    }

    @Test
    public void pullCode() {
        String code = git.pull();
        assertEquals("System.out.println(\"Hello, World!\");", code);
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
        assertEquals("new code", git.pull());
        assertEquals(null, git.getCommitTitle());
        assertEquals(null, git.getCommitCode());
    }
}