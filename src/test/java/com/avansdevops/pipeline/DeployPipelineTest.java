package com.avansdevops.pipeline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DeployPipelineTest {
    private PipelineTemplate deployPipeline = Mockito.spy(new DeployPipeline("Analysis"));

    @Test
    public void getPipelineNameReturnsCorrectName() {
        assertEquals("Analysis", deployPipeline.getPipelineName());
    }

    @Test
    public void pipelineSetupIsSuccessful() {
        assertTrue(deployPipeline.setup());
    }

    @Test
    public void pipelineRunIsSuccessful() {
        assertTrue(deployPipeline.run());
    }

    @Test
    public void pipelineTeardownIsSuccessful() {
        assertTrue(deployPipeline.teardown());
    }

    @Test
    public void executePipelineWorks() {
        // Call the method to test
        deployPipeline.executePipeline();

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(deployPipeline);
        inOrder.verify(deployPipeline).setup();
        inOrder.verify(deployPipeline).run();
        inOrder.verify(deployPipeline).teardown();
    }
}