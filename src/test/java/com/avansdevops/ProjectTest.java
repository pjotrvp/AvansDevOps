package com.avansdevops;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class ProjectTest {
    

    //Tests for requirement 1
    @Test
    public void projectHasBacklog() {
        Project project = new Project("Project 1");
        assertNotNull(project.getBacklog());
    }

    
    //requirement 1
    @Test
    public void CanAddSCMToProject() {
        Project project = new Project("Project 1");
        assertNotNull(project.getScm());
    }

    
}
