package com.avansdevops;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.pipeline.AnalysisPipeline;
import com.avansdevops.pipeline.BuildPipeline;
import com.avansdevops.pipeline.DeployPipeline;
import com.avansdevops.pipeline.PipelineTemplate;
import com.avansdevops.pipeline.TestPipeline;
import com.avansdevops.users.User;
import com.avansdevops.users.UserFactory;
import com.avansdevops.users.UserRole;

public class ProjectTest {
    private Project project;
    private UserFactory factory;
    private User developer;
    private User productOwner1;
    private User productOwner2;
    private User scrumMaster;
    private Scm scm;
    private BacklogItem item;
    private PipelineTemplate pipeline1;
    private PipelineTemplate pipeline2;
    private PipelineTemplate pipeline3;
    private PipelineTemplate pipeline4;

    @Before
    public void setUp() {
        project = new Project("Test Project");
        factory = new UserFactory();
        developer = factory.createUser(UserRole.DEVELOPER, "Developer");
        productOwner1 = factory.createUser(UserRole.PRODUCT_OWNER, "Product Owner 1");
        productOwner2 = factory.createUser(UserRole.PRODUCT_OWNER, "Product Owner 2");
        scrumMaster = factory.createUser(UserRole.SCRUM_MASTER, "Scrum Master");
        scm = new Scm("Git");
        item = new BacklogItem("Item 1", "Description 1", 3);
        pipeline1 = mock(BuildPipeline.class);
        pipeline2 = mock(TestPipeline.class);
        pipeline3 = mock(AnalysisPipeline.class);
        pipeline4 = mock(DeployPipeline.class);
    }

    @Test
    public void testProject() {
        assertNotNull(project);
        assertEquals("Test Project", project.getName());
    }

    @Test
    public void testBacklog() {
        assertNotNull(project.getBacklog());
    }

    @Test
    public void testAddBacklogItem() {
        project.addBacklogItem(item);
        assertTrue(project.getBacklogItems().contains(item));
        assertEquals(1, project.getBacklogItems().size());
    }

    @Test
    public void testAddDuplicateBacklogItem() {
        project.addBacklogItem(item);
        project.addBacklogItem(item);
        assertEquals(1, project.getBacklogItems().size());
        long count = project.getBacklogItems().stream().filter(p -> p.equals(item)).count();
        assertEquals(1, count);
    }

    @Test
    public void testDiscussionForum() {
        assertNotNull(project.getDiscussionForum());
        assertEquals("Discussion Forum", project.getDiscussionForum().getForumName());
    }

    @Test
    public void testAddMember() {
        project.addMember(developer);
        project.addMember(productOwner1);
        project.addMember(scrumMaster);
        assertEquals(3, project.getMembers().size());
        assertTrue(project.getMembers().contains(developer));
        assertTrue(project.getMembers().contains(productOwner1));
        assertTrue(project.getMembers().contains(scrumMaster));
    }

    @Test
    public void testAddDuplicateMember() throws IllegalArgumentException {
        project.addMember(developer);
        try {
            project.addMember(developer);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("User is already a member of the project", e.getMessage());
            long count = project.getMembers().stream().filter(p -> p.equals(developer)).count();
            assertEquals(1, count);
        }
    }

    @Test
    public void testAddMemberWithExistingProductOwner() throws IllegalArgumentException {
        project.addMember(productOwner1);
        try {
            project.addMember(productOwner2);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Project already has a Product Owner", e.getMessage());
            assertTrue(project.getMembers().contains(productOwner1));
            assertFalse(project.getMembers().contains(productOwner2));
        }
    }

    @Test
    public void testRemoveMemberFromProjectAndSprint() throws Exception {
        project.addMember(developer);
        Sprint sprint = project.addSprint(new Date(), new Date(), SprintGoal.PARTIAL_PRODUCT);
        sprint.addParticipant(developer);
        project.removeMember(developer);
        assertFalse(project.getMembers().contains(developer));
        assertEquals(0, project.getMembers().size());
        assertFalse(sprint.getParticipants().contains(developer));
        assertEquals(0, sprint.getParticipants().size());
    }

    @Test
    public void testRemoveNonExistingMember() throws IllegalArgumentException {
        try {
            project.removeMember(developer);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("User is not a member of the project", e.getMessage());
        }
    }

    @Test
    public void testRemoveMemberFromProject() throws Exception {
        project.addMember(developer);
        project.addMember(scrumMaster);
        Sprint sprint = project.addSprint(new Date(), new Date(), SprintGoal.PARTIAL_PRODUCT);
        sprint.addParticipant(scrumMaster);
        project.removeMember(developer);
        assertFalse(project.getMembers().contains(developer));
        assertEquals(1, project.getMembers().size());
        assertFalse(sprint.getParticipants().contains(developer));
        assertEquals(1, sprint.getParticipants().size());
    }

    @Test
    public void testGetPipelines() {
        assertEquals(4, project.getPipelines().size());
        assertTrue(project.getPipelines().get(0) instanceof BuildPipeline);
        assertTrue(project.getPipelines().get(1) instanceof TestPipeline);
        assertTrue(project.getPipelines().get(2) instanceof AnalysisPipeline);
        assertTrue(project.getPipelines().get(3) instanceof DeployPipeline);
    }

    @Test
    public void testExecutePipelinesSuccess() {
        when(pipeline1.executePipeline()).thenReturn(true);
        when(pipeline2.executePipeline()).thenReturn(true);
        when(pipeline3.executePipeline()).thenReturn(true);
        when(pipeline4.executePipeline()).thenReturn(true);

        project.getPipelines().add(pipeline1);
        project.getPipelines().add(pipeline2);
        project.getPipelines().add(pipeline3);
        project.getPipelines().add(pipeline4);
        boolean result = project.executePipelines();

        assertTrue(result);
        verify(pipeline1).executePipeline();
        verify(pipeline2).executePipeline();
        verify(pipeline3).executePipeline();
        verify(pipeline4).executePipeline();
    }

    @Test
    public void testExecutePipelinesBuildFailure() {
        when(pipeline1.executePipeline()).thenReturn(false);
        when(pipeline2.executePipeline()).thenReturn(true);
        when(pipeline3.executePipeline()).thenReturn(true);
        when(pipeline4.executePipeline()).thenReturn(true);

        project.getPipelines().add(pipeline1);
        project.getPipelines().add(pipeline2);
        project.getPipelines().add(pipeline3);
        project.getPipelines().add(pipeline4);
        boolean result = project.executePipelines();

        assertFalse(result);
        verify(pipeline1).executePipeline();
        verify(pipeline2, never()).executePipeline();
        verify(pipeline3, never()).executePipeline();
        verify(pipeline4, never()).executePipeline();
    }

    @Test
    public void testExecutePipelinesTestFailure() {
        when(pipeline1.executePipeline()).thenReturn(true);
        when(pipeline2.executePipeline()).thenReturn(false);
        when(pipeline3.executePipeline()).thenReturn(true);
        when(pipeline4.executePipeline()).thenReturn(true);

        project.getPipelines().add(pipeline1);
        project.getPipelines().add(pipeline2);
        project.getPipelines().add(pipeline3);
        project.getPipelines().add(pipeline4);
        boolean result = project.executePipelines();

        assertFalse(result);
        verify(pipeline1).executePipeline();
        verify(pipeline2).executePipeline();
        verify(pipeline3, never()).executePipeline();
        verify(pipeline4, never()).executePipeline();
    }

    @Test
    public void testExecutePipelinesAnalysisFailure() {
        when(pipeline1.executePipeline()).thenReturn(true);
        when(pipeline2.executePipeline()).thenReturn(true);
        when(pipeline3.executePipeline()).thenReturn(false);
        when(pipeline4.executePipeline()).thenReturn(true);

        project.getPipelines().add(pipeline1);
        project.getPipelines().add(pipeline2);
        project.getPipelines().add(pipeline3);
        project.getPipelines().add(pipeline4);
        boolean result = project.executePipelines();

        assertFalse(result);
        verify(pipeline1).executePipeline();
        verify(pipeline2).executePipeline();
        verify(pipeline3).executePipeline();
        verify(pipeline4, never()).executePipeline();
    }

    @Test
    public void testExecutePipelinesDeployFailure() {
        when(pipeline1.executePipeline()).thenReturn(true);
        when(pipeline2.executePipeline()).thenReturn(true);
        when(pipeline3.executePipeline()).thenReturn(true);
        when(pipeline4.executePipeline()).thenReturn(false);

        project.getPipelines().add(pipeline1);
        project.getPipelines().add(pipeline2);
        project.getPipelines().add(pipeline3);
        project.getPipelines().add(pipeline4);
        boolean result = project.executePipelines();

        assertFalse(result);
        verify(pipeline1).executePipeline();
        verify(pipeline2).executePipeline();
        verify(pipeline3).executePipeline();
        verify(pipeline4).executePipeline();
    }

    @Test
    public void testSprints() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        Date futureDate = calendar.getTime();
        Sprint sprint = project.addSprint(new Date(), futureDate, SprintGoal.PARTIAL_PRODUCT);
        assertEquals(1, project.getSprints().size());
        assertTrue(project.getSprints().contains(sprint));
    }

    @Test
    public void testScm() {
        project.setScm(scm);
        assertNotNull(project.getScm());
        assertEquals(scm, project.getScm());
    }
}
