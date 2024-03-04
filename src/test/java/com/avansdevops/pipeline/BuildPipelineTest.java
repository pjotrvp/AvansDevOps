package com.avansdevops.pipeline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avansdevops.pipeline.BuildPipeline;
import com.avansdevops.pipeline.PipelineTemplate;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BuildPipelineTest {
    private PipelineTemplate buildPipeline = Mockito.spy(new BuildPipeline("Build"));

    @Test
    public void getPipelineNameReturnsCorrectName() {
        assertEquals("Build", buildPipeline.getPipelineName());
    }

    @Test
    public void pipelineSetupIsSuccessful() {
        assertTrue(buildPipeline.setup());
    }

    @Test
    public void pipelineRunIsSuccessful() {
        assertTrue(buildPipeline.run());
    }

    @Test
    public void pipelineTeardownIsSuccessful() {
        assertTrue(buildPipeline.teardown());
    }

    @Test
    public void executePipelineWorks() {
        // Call the method to test
        buildPipeline.executePipeline();

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(buildPipeline);
        inOrder.verify(buildPipeline).setup();
        // inOrder.verify(buildPipeline).run();
        inOrder.verify(buildPipeline).teardown();
    }
}